import javafx.animation.RotateTransition; 
import javafx.application.Application;
import javafx.event.EventHandler; 

import javafx.scene.Group; 
import javafx.scene.PerspectiveCamera; 
import javafx.scene.Scene;

import javafx.scene.SubScene;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.scene.control.TextField; 
import javafx.scene.input.KeyEvent; 
import javafx.scene.paint.Color; 
import javafx.scene.paint.PhongMaterial;
 
import javafx.scene.shape.Box;

import javafx.scene.shape.Cylinder;

import javafx.scene.text.Font; 
import javafx.scene.text.FontWeight; 
import javafx.scene.text.Text;  
import javafx.scene.transform.Rotate; 
import javafx.stage.Stage; 
import javafx.util.Duration; 


import javafx.scene.SceneAntialiasing;

import javafx.geometry.Point3D;
         
public class App extends Application{
   
    private long time;
    private final IntegerProperty counter = new SimpleIntegerProperty();
    
    @Override
    public void start(Stage primaryStage) {
        /*Rotate rotate1 = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
        rotate1.angleProperty().bind(counter);
    
        Box box1 = new Box(100, 100, 100);
        box1.setMaterial(new PhongMaterial(Color.LIMEGREEN));
        box1.getTransforms().add(rotate1);
    
        Cylinder axis1 =  new Cylinder(2, 200);
        axis1.setRotationAxis(Rotate.X_AXIS);
        axis1.setRotate(90);
        axis1.setMaterial(new PhongMaterial(Color.RED));
    
        Group group1 = new Group(box1, axis1);
        group1.setTranslateX(-100);*/
    
        Rotate rotate2 = new Rotate(0, -50, -50, 0, Rotate.Z_AXIS);
        rotate2.angleProperty().bind(counter);
        
        Box box2 = new Box(100, 100, 100);
        box2.setMaterial(new PhongMaterial(Color.LAVENDER));
        box2.getTransforms().add(rotate2);
        
        //Setting the position of the box 
        box2.setTranslateX(0);
        box2.setTranslateY(0);
        box2.setTranslateZ(0);
        
        Rotate rotate3 = new Rotate(0, 50, -50, 0, Rotate.Z_AXIS);
        rotate3.angleProperty().bind(counter);

        Box box3 = new Box(100, 100, 100);
        box3.setMaterial(new PhongMaterial(Color.BROWN));
        box3.getTransforms().add(rotate3);

        //Setting the position of the box 
        box3.setTranslateX(-100);
        box3.setTranslateY(0);
        box3.setTranslateZ(0);

        Rotate rotate4 = new Rotate(0, 50, 50, 0, Rotate.Z_AXIS);
        rotate4.angleProperty().bind(counter);

        Box box4 = new Box(100, 100, 100);
        box4.setMaterial(new PhongMaterial(Color.DARKORANGE));
        box4.getTransforms().add(rotate4);

        //Setting the position of the box 
        box4.setTranslateX(-100);
        box4.setTranslateY(-100);
        box4.setTranslateZ(0);


        Rotate rotate5 = new Rotate(0, -50, 50, 0, Rotate.Z_AXIS);
        rotate5.angleProperty().bind(counter);

        Box box5 = new Box(100, 100, 100);
        box5.setMaterial(new PhongMaterial(Color.BROWN));
        box5.getTransforms().add(rotate5);

        //Setting the position of the box 
        box5.setTranslateX(0);
        box5.setTranslateY(-100);
        box5.setTranslateZ(0);
    
        Cylinder axis2 =  new Cylinder(2, 200);
        axis2.setRotationAxis(Rotate.X_AXIS);
        axis2.setRotate(90);
        axis2.setTranslateX(-50);
        axis2.setTranslateY(-50);
        axis2.setMaterial(new PhongMaterial(Color.FIREBRICK));
    
        Group group2 = new Group(box2, box3, box4, box5, axis2);
        group2.setTranslateX(200);
    
        Group root = new Group(/*group1,*/ group2);
    
        SubScene subScene = new SubScene(root, 600, 400, true, SceneAntialiasing.BALANCED);
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setTranslateX(-200);
        camera.setTranslateY(-200);
        camera.setTranslateZ(-100);
        camera.setRotationAxis(new Point3D(0, 0.5, 0.5));
        camera.setRotate(10);
        subScene.setCamera(camera);
        Scene scene = new Scene(new StackPane(subScene), 600, 400, true, SceneAntialiasing.BALANCED);
    
        primaryStage.setScene(scene);
        primaryStage.show();
    
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - time > 30_000_000) {
                    counter.set((counter.get() + 1) % 360);
                    time = now;
                }
            }
        };

        timer.start();
    }
   public static void main(String args[]){ 
      launch(args); 
   } 
}