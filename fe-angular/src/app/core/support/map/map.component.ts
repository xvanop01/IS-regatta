import {Component, OnInit} from '@angular/core';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import {ATTRIBUTION} from "ol/source/OSM";
import {fromLonLat, useGeographic} from "ol/proj";
import VectorSource from "ol/source/Vector";
import VectorLayer from "ol/layer/Vector";
import {Fill, Stroke, Style} from "ol/style";
import {MatButton} from "@angular/material/button";
import {LineString, MultiPoint, Point, Polygon} from "ol/geom";
import {Feature} from "ol";
import {NgIf} from "@angular/common";
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatDialogContent} from "@angular/material/dialog";
import CircleStyle from "ol/style/Circle";

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

  public map!: Map;

  public placingCourse: boolean = false;

  public trackVector: VectorSource = new VectorSource({features: []});

  public angleFormControl: FormControl = new FormControl(0, [Validators.min(0), Validators.max(359)]);

  public lastValidAngle: number = 0;

  ngOnInit(): void {
    const raceStyles = [
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
    const courseLayer = new VectorLayer({
      source: this.trackVector,
      style: raceStyles
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
    this.map = new Map({
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
        seaLayer,
        courseLayer
      ],
      target: 'map',
      view: new View({
        center: fromLonLat([17, 49.4]),
        zoom: 7,
        maxZoom: 18,
        zoomFactor: 2
      })
    });

    this.map.on('moveend', () => {
      this.refresh();
    });
  }

  public angleChanged(): void {
    if (this.angleFormControl.errors == null) {
      this.lastValidAngle = this.angleFormControl.value;
      this.refresh();
    }
  }

  public refresh(): void {
    if (this.placingCourse) {
      this.trackVector.clear();
      this.renderCourse();
    }
  }

  public renderCourse(): void {
    const coord = this.map.getView().getCenter();
    const mapSize = this.map.getSize();
    if (coord && mapSize) {
      const width = new LineString([this.map.getCoordinateFromPixel([0, mapSize[1]/2]), this.map.getCoordinateFromPixel([mapSize[0], mapSize[1]/2])]).getLength();
      const height = new LineString([this.map.getCoordinateFromPixel([mapSize[0]/2, 0]), this.map.getCoordinateFromPixel([mapSize[0]/2, mapSize[1]])]).getLength();
      const radius: number = (width < height ? width : height) * 0.4;
      const l1: number = radius;
      const l2: number = radius/2;
      const l3: number = Math.sqrt((radius * radius) - ((radius/2)*(radius/2)));
      const c1: Array<number> = [coord[0] + l2, coord[1] + l3];
      const c2: Array<number> = [coord[0] - l1, coord[1]];
      const c3: Array<number> = [coord[0] + l2, coord[1] - l3];
      const track: Array<Array<number>> = [c1, c2, c3];
      console.log([c1, c2, c3]);
      const polygon = new Polygon([track]);
      if (this.lastValidAngle != 0) {
        polygon.rotate(this.lastValidAngle * Math.PI / 180, coord);
      }
      this.trackVector.addFeature(new Feature({
        geometry: polygon
      }));
      this.trackVector.addFeature(new Feature({
        geometry: new MultiPoint(polygon.getCoordinates()[0])
      }));
    }
  }

  public placeCourseClicked(): void {
    this.placingCourse = !this.placingCourse;
    if (this.placingCourse) {
      this.renderCourse();
    } else {
      this.trackVector.clear();
    }
  }
}
