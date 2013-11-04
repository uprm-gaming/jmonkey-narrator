package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class NarratorGuiTest extends SimpleApplication 
{
    public static void main(String[] args)
    {
        AppSettings customSettings = new AppSettings(true);
        customSettings.setTitle("Narrator Gui Node Test");
        customSettings.setResolution(800, 600);
        
        NarratorGuiTest app = new NarratorGuiTest();
        app.setSettings(customSettings);
        app.setShowSettings(false);
        app.setPauseOnLostFocus(false);
        app.start(); // calls simpleInitApp()
    }
    private NarratorGuiNode gameNarrator;

    @Override
    public void simpleInitApp() 
    {
        shutDownDefaultHUD();
        initializeNarrator();
        displayColoredBox(ColorRGBA.Blue);
        flyCam.setMoveSpeed(15.0f);
    } 
    
    private void shutDownDefaultHUD()
    {
        setDisplayFps(false);
        setDisplayStatView(false);
    }
    
    private void initializeNarrator()
    {
        gameNarrator = new NarratorGuiNode(assetManager, guiNode);
        stateManager.attach(gameNarrator);
        gameNarrator.talk(assetManager, "Hi there.", "Interface/bluebox1.wav");
        System.out.println(guiNode);
    }
    
    private void displayColoredBox(ColorRGBA color)
    {
        Geometry boxGeometry = new Geometry("Box", new Box(1, 1, 1));
        Material boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMaterial.setColor("Color", color);
        boxGeometry.setMaterial(boxMaterial);
        rootNode.attachChild(boxGeometry);
    }
    
    @Override
    public void simpleUpdate(float tpf)
    {}
}