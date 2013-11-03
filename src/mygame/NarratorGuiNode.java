package mygame;

import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;

public class NarratorGuiNode extends AbstractAppState
{
    private AssetManager assetManager;
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
    
    public void talk(String text, String audioPathFile) 
    {
        talk(text);
        playAudioFile(audioPathFile);
    }
    
    private void talk(String text)
    {
        if (narratorText.getCullHint() == CullHint.Never)
            narratorText.setCullHint(CullHint.Always);
        narratorText.setCullHint(Spatial.CullHint.Always);
        narratorText.setText(text);
        System.out.println("Made it here.");
    }
    
    private void playAudioFile(String path)
    {
        flushAudio();
        //narratorAudio = new AudioNode(assetManager, "Interface/sounds/bluebox1.wav", false);
        //narratorAudio.setPitch(1.1f);
        //narratorAudio.play();
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
    public void update(float tpf)
    {

    }
}
