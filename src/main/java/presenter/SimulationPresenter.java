package presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.*;

import static java.lang.Math.min;


public class SimulationPresenter {
    private final static int MAP_SIZE = 800;
    private Intersection intersection;
    private int laneNumber;
    private int size;
    @FXML
    private GridPane mapGrid;

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public void setLaneNumber(int laneNumber) {
        this.laneNumber = laneNumber;
    }

    private void drawMap() {
        clearGrid();
        size = MAP_SIZE / (laneNumber * 8);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(size));
        mapGrid.getRowConstraints().add(new RowConstraints(size));
        for (int row = 0; row < laneNumber * 8; row++) {
            for (int col = 0; col < laneNumber * 8; col++) {
                javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(size, size);
                rect.setFill(Color.LIGHTGRAY);
                if ((row < 3 * laneNumber && col < 3 * laneNumber) || (row < 3 * laneNumber && col >= 5 * laneNumber) || (row >= 5 * laneNumber && col < 3 * laneNumber) || (row >= 5 * laneNumber && col >= 5 * laneNumber)) {
                    rect.setFill(Color.GREEN);
                }
                mapGrid.add(rect, col, row);
            }
        }
        for (Directions direction : Directions.values()) {
            drawVehiclesAndLights(direction, mapGrid, size);
            drawRightArrow(direction, mapGrid, size);
            addNumberOfWaitingCars(direction, mapGrid, size);
        }

    }

    private void drawRightArrow(Directions direction, GridPane mapGrid, int size) {
        Image rightArrow;
        Road road = intersection.getRoad(direction);
        if (road.isRightArrowOn()) {
            rightArrow = new Image("images/righton.png");
        } else {
            rightArrow = new Image("images/rightoff.png");
        }
        ImageView imageView = new ImageView(rightArrow);
        rotateImage(direction, imageView);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        switch (direction) {
            case SOUTH -> mapGrid.add(imageView, 5 * laneNumber, 5 * laneNumber);
            case NORTH -> mapGrid.add(imageView, 3 * laneNumber - 1, 3 * laneNumber - 1);
            case WEST -> mapGrid.add(imageView, 3 * laneNumber - 1, 5 * laneNumber);
            case EAST -> mapGrid.add(imageView, 5 * laneNumber, 3 * laneNumber - 1);
        }
    }

    private void addNumberOfWaitingCars(Directions direction, GridPane mapGrid, int size) {

        for (Lane lane : intersection.getRoad(direction).getLanes()) {
            for (int i = 0; i < laneNumber; i++) {
                Label text = new Label(String.valueOf(lane.sizeOfQueue() + lane.sizeOfQueueOnIntersection()));
                GridPane.setHalignment(text, HPos.CENTER);
                text.setFont(new Font("Arial", 24));
                switch (direction) {
                    case SOUTH -> mapGrid.add(text, 4 * laneNumber + lane.getNumberOfLane(), 8 * laneNumber - 1);
                    case NORTH -> mapGrid.add(text, 4 * laneNumber - 1 - lane.getNumberOfLane(), 0);
                    case WEST -> mapGrid.add(text, 0, 5 * laneNumber - 1 - lane.getNumberOfLane());
                    case EAST -> mapGrid.add(text, 8 * laneNumber - 1, 4 * laneNumber - lane.getNumberOfLane() - 1);
                }
            }
        }
    }

    private void drawVehicles(Directions direction, GridPane mapGrid, int size) {

    }

    private void drawVehiclesAndLights(Directions direction, GridPane mapGrid, int size) {
        Road road = intersection.getRoad(direction);
        for (Lane lane : road.getLanes()) {
            Image light;
            Light color = road.color();
            switch (color) {
                case RED -> light = new Image("images/czerwone.png");
                case ORANGE -> light = new Image("images/pomaranczowe.png");
                case REDTOGREEN -> light = new Image("images/REDTOGREEN.png");
                case GREEN -> light = new Image("images/zielone.png");
                default -> light = new Image("images/brak.png");
            }
            ImageView imageView = new ImageView(light);
            imageView.setFitWidth(size);
            imageView.setFitHeight(size);
            switch (direction) {
                case SOUTH -> mapGrid.add(imageView, 4 * laneNumber + lane.getNumberOfLane(), 5 * laneNumber);
                case NORTH -> mapGrid.add(imageView, 4 * laneNumber - 1 - lane.getNumberOfLane(), 3 * laneNumber - 1);
                case WEST -> mapGrid.add(imageView, 3 * laneNumber - 1, 5 * laneNumber - 1 - lane.getNumberOfLane());
                case EAST -> mapGrid.add(imageView, 5 * laneNumber, 4 * laneNumber - lane.getNumberOfLane() - 1);
            }


            int maxToDraw = min(laneNumber * 3 - 1, lane.sizeOfQueue());
            for (int i = 0; i < maxToDraw; i++) {
                Image image = new Image("images/auto.png");
                ImageView imageView2 = new ImageView(image);
                imageView2.setFitWidth(size);
                imageView2.setFitHeight(size);
                rotateImage(direction, imageView2);
                switch (direction) {
                    case SOUTH ->
                            mapGrid.add(imageView2, 4 * laneNumber + lane.getNumberOfLane(), 5 * laneNumber + i + 1);
                    case NORTH ->
                            mapGrid.add(imageView2, 4 * laneNumber - 1 - lane.getNumberOfLane(), 3 * laneNumber - i - 2);
                    case WEST ->
                            mapGrid.add(imageView2, 3 * laneNumber - i - 2, 5 * laneNumber - 1 - lane.getNumberOfLane());
                    case EAST ->
                            mapGrid.add(imageView2, 5 * laneNumber + i + 1, 4 * laneNumber - lane.getNumberOfLane() - 1);
                }
            }
            if (lane.getNumberOfLane() == 0) {
                for (int i = 0; i < lane.sizeOfQueueOnIntersection(); i++) {
                    Image image = new Image("images/auto.png");
                    ImageView imageView3 = new ImageView(image);
                    imageView3.setFitWidth(size);
                    imageView3.setFitHeight(size);
                    rotateImage(direction, imageView3);
                    switch (direction) {
                        case SOUTH -> mapGrid.add(imageView3, 4 * laneNumber, 4 * laneNumber + i);
                        case NORTH -> mapGrid.add(imageView3, 4 * laneNumber - 1, 4 * laneNumber - i - 1);
                        case WEST -> mapGrid.add(imageView3, 4 * laneNumber - i - 1, 5 * laneNumber - 1);
                        case EAST -> mapGrid.add(imageView3, 4 * laneNumber + i, 4 * laneNumber - 1);
                    }
                }
            }


        }
    }

    private void rotateImage(Directions direction, ImageView imageView) {
        switch (direction) {
            case SOUTH -> imageView.setRotate(0);
            case NORTH -> imageView.setRotate(180);
            case WEST -> imageView.setRotate(90);
            case EAST -> imageView.setRotate(270);
        }
    }

    public void mapChanged(Intersection intersection) {
        setIntersection(intersection);
        Platform.runLater(this::drawMap);
    }

    private void clearGrid() {
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}