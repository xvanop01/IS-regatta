import { Component, OnInit } from '@angular/core';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import {ATTRIBUTION} from "ol/source/OSM";
import {useGeographic} from "ol/proj";

@Component({
  selector: 'app-map',
  standalone: true,
  templateUrl: 'map.component.html',
  styleUrls: ['map.component.css'],
  imports: []
})
export class MapComponent implements OnInit{

  public map!: Map;

  ngOnInit(): void {
    useGeographic();
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
        seaLayer
      ],
      target: 'map',
      view: new View({
        center: [17, 49.4],
        zoom: 7, maxZoom: 18,
      })
    });

    this.map.on('moveend', () => {
      console.log(this.map.getView().getCenter());
    });
  }

  placeTrack(): void {

  }
}
