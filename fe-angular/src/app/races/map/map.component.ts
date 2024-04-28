import {Component, Input, OnInit} from '@angular/core';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import {ATTRIBUTION} from "ol/source/OSM";
import {fromLonLat} from "ol/proj";
import VectorSource from "ol/source/Vector";
import VectorLayer from "ol/layer/Vector";
import {Fill, Icon, Stroke, Style} from "ol/style";
import {MatButton} from "@angular/material/button";
import {LineString, MultiPoint, Point, Polygon} from "ol/geom";
import {Feature} from "ol";
import {NgIf} from "@angular/common";
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatDialogContent} from "@angular/material/dialog";
import CircleStyle from "ol/style/Circle";
import Geolocation from 'ol/Geolocation.js';
import {CourseDetailDto, CreateUpdateCourseDto} from "../races.model";
import {ActivatedRoute} from "@angular/router";
import {RacesService} from "../races.service";
import {MatSnackBar} from "@angular/material/snack-bar";

const courseStyles = [
  new Style({
    stroke: new Stroke({
      color: 'blue',
      width: 1,
    })
  }),
  new Style({
    image: new CircleStyle({
      fill: new Fill({
        color: 'yellow',
      }),
      radius: 7
    })
  })
];

@Component({
  selector: 'app-map',
  standalone: true,
  templateUrl: 'map.component.html',
  styleUrls: ['map.component.css'],
  imports: [
    MatButton,
    NgIf,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInput,
    MatDialogContent
  ]
})
export class MapComponent implements OnInit{

  @Input()
  public canEdit: boolean = false;

  public map!: Map;

  public editingCourse: boolean = false;

  public navigating: boolean = false;

  public trackVector: VectorSource = new VectorSource({features: []});

  public navVector: VectorSource = new VectorSource({features: []});

  public angleFormControl: FormControl = new FormControl(0, [Validators.min(0), Validators.max(359)]);

  public lastValidAngle: number = 0;

  public untransformedCoordinates: Array<Array<number>> = [];

  public savedCourse: CourseDetailDto | undefined;

  public raceId: number;

  constructor(private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private racesService: RacesService) {
    this.raceId = Number(this.route.snapshot.paramMap.get('raceId'));
  }

  ngOnInit(): void {
    const courseLayer = new VectorLayer({
      source: this.trackVector,
      style: courseStyles
    });
    const seaLayer = new TileLayer({
      source: new OSM({
        attributions: [
          '© <a href="https://www.openseamap.org/">OpenSeaMap</a>',
          ATTRIBUTION
        ],
        opaque: false,
        url: 'https://tiles.openseamap.org/seamark/{z}/{x}/{y}.png'
      })
    });
    const view = new View({
      center: fromLonLat([17, 49.4]),
      zoom: 7,
      maxZoom: 18,
      zoomFactor: 2
    })
    const navigationLayer = new VectorLayer({
      source: this.navVector
    });
    this.map = new Map({
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
        seaLayer,
        courseLayer,
        navigationLayer
      ],
      target: 'map',
      view: view
    });

    this.map.on('moveend', () => {
      this.refresh();
    });

    this.racesService.getCourse(this.raceId).subscribe(result => {
      if (result) {
        this.savedCourse = result;
        this.renderSaved();
      }
    });
  }

  public angleChanged(): void {
    if (this.angleFormControl.errors == null) {
      this.lastValidAngle = this.angleFormControl.value;
      this.refresh();
    }
  }

  public refresh(): void {
    if (this.editingCourse) {
      this.trackVector.clear();
      this.renderCourse();
    }
  }

  public renderCourse(): void {
    const coord = this.map.getView().getCenter();
    const mapSize = this.map.getSize();
    if (coord && mapSize) {
      const width = new LineString([
        this.map.getCoordinateFromPixel([0, mapSize[1]/2]),
        this.map.getCoordinateFromPixel([mapSize[0], mapSize[1]/2])
      ]).getLength();
      const height = new LineString([
        this.map.getCoordinateFromPixel([mapSize[0]/2, 0]),
        this.map.getCoordinateFromPixel([mapSize[0]/2, mapSize[1]])
      ]).getLength();
      const radius: number = (width < height ? width : height) * 0.4;
      const l1: number = radius;
      const l2: number = radius/2;
      const l3: number = Math.sqrt((radius * radius) - ((radius/2)*(radius/2)));
      const c1: Array<number> = [coord[0] + l2, coord[1] + l3];
      const c2: Array<number> = [coord[0] - l1, coord[1]];
      const c3: Array<number> = [coord[0] + l2, coord[1] - l3];
      const track: Array<Array<number>> = [c1, c2, c3];
      this.untransformedCoordinates = track;
      const polygon = new Polygon([track]);
      if (this.lastValidAngle != 0) {
        polygon.rotate(- this.lastValidAngle * Math.PI / 180, coord);
      }
      this.trackVector.addFeature(new Feature({
        geometry: polygon
      }));
      this.trackVector.addFeature(new Feature({
        geometry: new MultiPoint(polygon.getCoordinates()[0])
      }));
    }
  }

  public editCourseClicked(): void {
    this.editingCourse = !this.editingCourse;
    this.trackVector.clear();
    if (this.editingCourse) {
      if (this.savedCourse) {
        this.map.getView().setCenter([this.savedCourse.center.longitude, this.savedCourse.center.latitude])
        this.angleFormControl.patchValue(this.savedCourse.windAngle);
        this.angleChanged();
      }
      this.renderCourse();
    } else {
      this.renderSaved();
    }
  }

  public saveCourse(): void {
    const center = this.map.getView().getCenter();
    const zoom = this.map.getView().getZoom();
    if (center && zoom) {
      const dto: CreateUpdateCourseDto = {
        buoy1: {longitude: this.untransformedCoordinates[0][0], latitude: this.untransformedCoordinates[0][1]},
        buoy2: {longitude: this.untransformedCoordinates[1][0], latitude: this.untransformedCoordinates[1][1]},
        buoy3: {longitude: this.untransformedCoordinates[2][0], latitude: this.untransformedCoordinates[2][1]},
        center: {longitude: center[0], latitude: center[1]},
        zoom: zoom,
        windAngle: this.lastValidAngle
      };
      this.racesService.createUpdateCourse(this.raceId, dto).subscribe(result => {
        this.savedCourse = result;
        this.editingCourse = false;
      }, (error) => {
        this.snackBar.open(error.status + ': ' + error.error, 'X');
      })
    }
  }

  public renderSaved(): void {
    if (this.savedCourse) {
      const polygon = new Polygon([[
        [this.savedCourse.buoy1.longitude, this.savedCourse.buoy1.latitude],
        [this.savedCourse.buoy2.longitude, this.savedCourse.buoy2.latitude],
        [this.savedCourse.buoy3.longitude, this.savedCourse.buoy3.latitude],
      ]]);
      const center = [this.savedCourse.center.longitude, this.savedCourse.center.latitude];
      if (this.savedCourse.windAngle != 0) {
        polygon.rotate(- this.savedCourse.windAngle * Math.PI / 180, center);
      }
      this.trackVector.addFeature(new Feature({
        geometry: polygon
      }));
      this.trackVector.addFeature(new Feature({
        geometry: new MultiPoint(polygon.getCoordinates()[0])
      }));
      this.map.getView().setCenter(center);
      this.map.getView().setZoom(this.savedCourse.zoom);
    }
  }

  public navigate(): void {
    if (this.navigating) {
      this.navVector.clear();
    } else {
      let icon = new Icon({
        src: '../../../assets/navigation-position.svg',
        scale: 0.4,
        rotateWithView: true
      });
      const positionStyle = new Style({
        image: icon
      });
      let view = this.map.getView();
      const geoFeature = new Feature();
      geoFeature.setStyle(positionStyle);
      const geolocation = new Geolocation({
        trackingOptions: {
          enableHighAccuracy: true,
        },
        projection: view.getProjection(),
        tracking: true
      });
      geolocation.on('change:heading', function () {
        const heading = geolocation.getHeading();
        if (heading) {
          icon.setRotation(heading);
        }
      });
      geolocation.on('change:position', function () {
        const coordinates = geolocation.getPosition();
        geoFeature.setGeometry(coordinates ? new Point(coordinates) : undefined);
        view.setCenter(coordinates);
      });
      geolocation.on('error', function (error) {
        console.error('Location error: ' + error.message);
      });
      this.navVector.addFeature(geoFeature);
    }
    this.navigating = !this.navigating;
  }
}
