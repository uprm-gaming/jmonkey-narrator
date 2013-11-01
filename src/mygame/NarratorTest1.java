package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class NarratorTest1 extends SimpleApplication implements ActionListener {
    NarratorNiftyGui gameNarrator;
    
    private static final String MAPPING_FIRST_MSG = "narrator first Message";
    private static final String MAPPING_SECOND_MSG = "narrator second Message";
    private static final String MAPPING_THIRD_MSG = "narrator third Message";
    
    private static final Trigger TRIGGER_FIRST_MSG = new KeyTrigger(KeyInput.KEY_1);
    private static final Trigger TRIGGER_SECOND_MSG = new KeyTrigger(KeyInput.KEY_2);
    private static final Trigger TRIGGER_THIRD_MSG = new KeyTrigger(KeyInput.KEY_3);
    
    public static void main(String[] args) 
    {
        AppSettings customSettings = new AppSettings(true);
        customSettings.setTitle("Narrator Test 1");
        customSettings.setResolution(800, 600);
        
        NarratorTest1 app = new NarratorTest1();
        app.setSettings(customSettings);
        app.setShowSettings(false);
        app.setPauseOnLostFocus(false);
        app.start();
    }
    
    @Override
    public void simpleInitApp() 
    {
        shutDownDefaultHUD();
        gameNarrator = getGameNarrator();
        createAndDisplayBox();
        initKeyboardControls();
        flyCam.setMoveSpeed(20.0f);
    }
    
    private void shutDownDefaultHUD()
    {
        setDisplayFps(false);
        setDisplayStatView(false);
    }
    
    private NarratorNiftyGui getGameNarrator() 
    {
        return NarratorNiftyGui.newInstance(assetManager, inputManager, 
                                            audioRenderer, guiViewPort);
    }
    
    private void createAndDisplayBox() 
    {
        Geometry boxGeometry = new Geometry("Box", new Box(1, 1, 1));
        Material boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMaterial.setColor("Color", ColorRGBA.Blue);
        boxGeometry.setMaterial(boxMaterial);
        rootNode.attachChild(boxGeometry);
    }
    
    private void initKeyboardControls() 
    {
        String[] mappings = {MAPPING_FIRST_MSG, MAPPING_SECOND_MSG, MAPPING_THIRD_MSG};
        Trigger[] triggers = {TRIGGER_FIRST_MSG, TRIGGER_SECOND_MSG, TRIGGER_THIRD_MSG};

        for (int i = 0; i < mappings.length; i++) {
            inputManager.addMapping(mappings[i], triggers[i]);
            inputManager.addListener(this, mappings[i]);
        }
    }
    
    @Override
    public void onAction(String name, boolean isKeyPressed, float tpf) {
        switch (name)
        {
            case MAPPING_FIRST_MSG:
                if (!isKeyPressed)
                    gameNarrator.talk("Hey there.");
                break;
            case MAPPING_SECOND_MSG:
                if (!isKeyPressed)
                    gameNarrator.talk("I'm a blue box.");
                break;
            case MAPPING_THIRD_MSG:
                if (!isKeyPressed)
                    gameNarrator.talk("Are you bored? Because I'm certainly not.");
                break;
            default:
                break;
        }
    }
   
    @Override
    public void simpleUpdate(float tpf) 
    {}
}