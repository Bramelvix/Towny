package entity.nonDynamic.building.farming;


import entity.nonDynamic.building.BuildAbleObject;

abstract class FarmingObj extends BuildAbleObject {

    FarmingObj() {
        super();
        this.setOpened(true);
    }
}
