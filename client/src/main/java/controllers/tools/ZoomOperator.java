package controllers.tools;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.util.Duration;

public class ZoomOperator {

    private Timeline timeline;
    private Bounds bounds;
    private final double maxW = 2000;
    private final double maxH = 2000;
    public ZoomOperator() {
        this.timeline = new Timeline(60);
        bounds = new BoundingBox(0,0,0,0);
    }

    public void zoom(Node node, double factor, double x, double y) {
        // determine scale
        double oldScale = node.getScaleX();
        double scale = oldScale * factor;
        double f = (scale / oldScale) - 1;

        // determine offset that we will have to move the node
        bounds = node.localToScene(node.getBoundsInLocal());
        double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
        double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));
        /*
        if(f<0) {
            if ((bounds.getMaxX() <= maxW) || (bounds.getMinX() >= 0)) {
                dx=0;
                scale=0;
            }
            if ((bounds.getMaxY() <= maxH) || (bounds.getMinY() >= 0)) {
                dy=0;
                scale=0;
            }
        }*/
        // timeline that scales and moves the node
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), node.getTranslateX() - f * dx)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateYProperty(), node.getTranslateY() - f * dy)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.scaleXProperty(), scale)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.scaleYProperty(), scale))
        );
        timeline.play();
    }
    public Bounds getBounds(){
        return bounds;
    }
}