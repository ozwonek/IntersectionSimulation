package model;

public class Vehicle {
    private final String vehicleId;
    private final Directions startRoad;
    private final Directions endRoad;
    private final Turns turn;

    public Vehicle(String vehicleId, Directions startRoad, Directions endRoad){
        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
        this.turn = Turns.whatTurn(startRoad,endRoad);
    }

    public Directions getEndRoad() {
        return endRoad;
    }

    public Directions getStartRoad() {
        return startRoad;
    }

    public String getVehicleId() {
        return vehicleId;
    }
    public Turns getTurn(){
        return turn;
    }

}
