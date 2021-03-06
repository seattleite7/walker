import {State} from '../../typings/state';
import {Action} from '../../typings/action';
import {Actions} from '../../redux/actions';
import {StatechangeEvent} from '../../typings/statechange-event';
import {Marker} from '../../typings/marker';
import {Options} from '../../typings/options';
import {UtilitiesService} from '../../services/utilities-service';

export class WalkerMarkerModal {
  public is: string;
  public querySelector: any;
  public latitude: number;
  public longitude: number;
  public action: Action;
  public title: string;
  public markerId: string;
  public openingTime: string;
  public closingTime: string;
  public successMessage: string;
  public errorMessage: string;
  public building: boolean;
  public properties: any;
  public isBuildingSelection: 'yes' | 'no' | 'neither';
  public buildings: Marker[];
  public buildingId: string;
  public selectedBuildingIndex: number;
  public isStairs: boolean;

  beforeRegister(): void {
    this.is = 'walker-marker-modal';
    this.properties = {
      building: {
        observer: 'changeIsBuildingSelection'
      }
    }
  }
  thisIsStairs(): void {
    this.isStairs = true;
  }
  thisIsNotStairs(): void {
    this.isStairs = false;
  }
  thisIsABuilding(): void {
    this.building = true;
  }

  thisIsNotABuilding(): void {
    this.building = false;
  }

  changeIsBuildingSelection(): void {
    this.isBuildingSelection = this.building ? 'yes' : 'no';
  }

  /**
   * This gets the building names and sets the index of the chosen building
   */
  async getBuildings(): Promise<void> {
    const buildings = await Actions.POST('getBuildings');
    const buildingsArray = JSON.parse(buildings);
    let wasIndexSet: boolean = false;
    for(let i: number = 0; i < buildingsArray.length; i++) {
      buildingsArray[i] = JSON.parse(buildingsArray[i]);
      if(buildingsArray[i].id === this.buildingId) {
        this.selectedBuildingIndex = i;
        wasIndexSet = true;
      }
    }
    this.buildings = buildingsArray;
    if(!wasIndexSet) {
      // had to hack this so that it will always change
      this.selectedBuildingIndex = -2;
      this.selectedBuildingIndex = -1;
    }
  }

  setBuilding(e: any): void {
    const buildingId: string = e.target.id;
    this.buildingId = buildingId;
  }
  /**
   * This gets called from walker-map
   */
  open(): void {
    this.querySelector('#modal').open();

  }
  /**
   * Called when user clicks done in modal
   */
  async setMarker(): Promise<void> {
    try {
      const marker: Marker = {
        latitude: this.latitude,
        longitude: this.longitude,
        title: this.title,
        id: this.markerId || '',
        openingTime: this.openingTime,
        closingTime: this.closingTime,
        building: this.building,
        buildingId: this.buildingId,
        isStairs: this.isStairs
      };

      await Actions.POST('setMarker', JSON.stringify(marker));
      await Actions.initMarkers(this, 'getMarkers');
      Actions.resetMarkerModal(this);
      this.successMessage = '';
      this.successMessage = 'Marker set';
    } catch(error) {
      this.errorMessage = '';
      this.errorMessage = error.message;
    }

  }

  async deleteMarker(e: any): Promise<void> {
    try {
      const marker: Marker = {
        latitude: this.latitude,
        longitude: this.longitude,
        id: this.markerId,
        title: this.title,
        openingTime: this.openingTime,
        closingTime: this.closingTime,
        building: this.building,
        buildingId: this.buildingId
      };

      await Actions.POST('deleteMarker', JSON.stringify(marker));

      await Actions.initMarkers(this, 'getMarkers');
      await Actions.resetMarkerModal(this);
      this.successMessage = '';
      this.successMessage = 'Marker deleted';
    } catch(error) {
      this.errorMessage = '';
      this.errorMessage = error.message;
    }

  }

  clearMarkers(e: any): void {
      Actions.initMarkers(this, 'getMarkers');
  }

  mapStateToThis(e: StatechangeEvent): void {
    const state: State = e.detail.state
    if(state.currentMarker) {
      this.latitude = state.currentMarker.latitude;
      this.longitude = state.currentMarker.longitude;
      this.markerId = state.currentMarker.id;
      this.title = state.currentMarker.title;
      this.openingTime = state.currentMarker.openingTime;
      this.closingTime = state.currentMarker.closingTime;
      this.building = UtilitiesService.isDefined(this.openingTime)
                   || UtilitiesService.isDefined(this.closingTime)
                   || UtilitiesService.isDefined(this.title);
      this.buildingId = !this.building ? state.currentMarker.buildingId : this.buildingId;
      this.getBuildings();

    }
  }

}

Polymer(WalkerMarkerModal);
