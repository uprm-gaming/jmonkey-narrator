package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;

public class NarratorGuiNode extends AbstractAppState
{
    private SimpleApplication simpleApp;
    private BitmapText narratorText;
    private AudioNode narratorAudio;
    
    public NarratorGuiNode(AssetManager assetManager, Node guiNode)
    {
        BitmapFont narratorTextFont = assetManager.loadFont("Interface/ArialRoundedMTBold.fnt");
        narratorText = new BitmapText(narratorTextFont);
        narratorText.setSize(narratorTextFont.getCharSet().getRenderedSize());
        narratorText.move(400, 300, 1);
        guiNode.attachChild(narratorText);
    }
    
    public void talk(AssetManager assetManager, String text, String audioPathFile) 
    {
        talk(text);
        playAudioFile(assetManager, audioPathFile);
    }
    
    private void talk(String text)
    {
        if (narratorText.getCullHint() == CullHint.Always)
            narratorText.setCullHint(CullHint.Never);
        narratorText.setText(text);
        System.out.println("Made it here.");
    }
    
    private void playAudioFile(AssetManager assetManager, String path)
    {
        flushAudio();
        narratorAudio = new AudioNode(assetManager, path, false);
        narratorAudio.setPitch(1.1f);
        narratorAudio.play();
    }
    
    private void flushAudio()
    {
        if (narratorAudio != null) 
        {
            narratorAudio.stop();
            narratorAudio = null;
        }
    }
    
    public boolean hasStoppedTalking()
    {
        return narratorAudio.getStatus() == AudioSource.Status.Stopped;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
        super.initialize(stateManager, app);
        simpleApp = (SimpleApplication) app;
    }

    @Override
    public void update(float tpf)
    {
        if (narratorText == null)
            return;
        
        if (hasStoppedTalking())
            narratorText.setCullHint(CullHint.Always);
    }
}
