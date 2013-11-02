package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.AudioSource;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;

public class NarratorNiftyGui extends AbstractAppState
{
    private AssetManager assetManager;
    private InputManager inputManager;
    private AudioRenderer audioRenderer;
    private ViewPort guiViewPort;
    private Nifty nifty;
    private Element narratorPanel;
    private AudioNode narratorVoicedText;
    
    private NarratorNiftyGui(AssetManager assetManager, InputManager inputManager, 
                            AudioRenderer audioRenderer, ViewPort guiViewPort) 
    {
        this.assetManager = assetManager;
        this.inputManager = inputManager;
        this.audioRenderer = audioRenderer;
        this.guiViewPort = guiViewPort;
        createNiftyScreen();
        initializeNarratorPanel();
    }
    
    private void createNiftyScreen()
    {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/screen.xml", "start");
        guiViewPort.addProcessor(niftyDisplay);
    }
    
    private void initializeNarratorPanel()
    {
        narratorPanel = getNiftyElement("narrator_panel");
        narratorPanel.hide();
    }
    
    public static NarratorNiftyGui newInstance(AssetManager assetManager, 
                                               InputManager inputManager, 
                                               AudioRenderer audioRenderer, 
                                               ViewPort guiViewPort) 
    {
        return new NarratorNiftyGui(assetManager, inputManager, audioRenderer, guiViewPort);
    }

    public void talk(String text, String audioPathFile) 
    {
        talk(text);
        playAudioFile(audioPathFile);
    }
    
    private void talk(String text)
    {
        if (!narratorPanel.isVisible())
            narratorPanel.show();

        getNiftyElement("narrator_text").getRenderer(TextRenderer.class).setText(text);
    }
    
    private void playAudioFile(String path)
    {
        flushAudio();
        narratorVoicedText = new AudioNode(assetManager, path, false);
        narratorVoicedText.play();
    }
    
    private void flushAudio()
    {
        if (narratorVoicedText != null) 
        {
            narratorVoicedText.stop();
            narratorVoicedText = null;
        }
    }
    
    public boolean hasStoppedTalking()
    {
        if (narratorVoicedText == null)
            return false;

        return narratorVoicedText.getStatus() == AudioSource.Status.Stopped;
    }

    private Element getNiftyElement(final String id) 
    {
        return nifty.getCurrentScreen().findElementByName(id);
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
        super.initialize(stateManager, app);
    }
    
    @Override
    public void update(float tpf)
    {
       if (hasStoppedTalking())
           narratorPanel.hide();
    }
}