package ravenweave.client.module.modules.movement;

import net.weavemc.loader.api.event.SubscribeEvent;
import ravenweave.client.event.MoveInputEvent;
import ravenweave.client.Raven;
import ravenweave.client.module.Module;
import ravenweave.client.module.setting.impl.SliderSetting;
import ravenweave.client.module.setting.impl.TickSetting;
import ravenweave.client.utils.Utils;

public class Speed extends Module {
    public static SliderSetting pspd;
    public static TickSetting lowhop;

    public Speed() {
        super("Speed", ModuleCategory.movement);
        this.registerSetting(pspd = new SliderSetting("Speed", 2.0D, 1.0D, 10.0D, 0.2D));
        this.registerSetting(lowhop = new TickSetting("Lowhop", false));
    }

    @SubscribeEvent
    public void onMoveInput(MoveInputEvent e) {
        Module fly = Raven.moduleManager.getModuleByName("Fly");
        if (fly != null && !fly.isEnabled() && Utils.Player.isMoving() && !mc.thePlayer.isInWater()) {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            }

            if (lowhop.isToggled()) {
                if (mc.thePlayer.fallDistance < 2 && mc.thePlayer.fallDistance > 0) {
                    mc.thePlayer.motionY *= 0.6;
                }
            }

            mc.thePlayer.setSprinting(true);
            double spd = 0.01D * pspd.getInput();
            double m = (float)(Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + spd);
            Utils.Player.bop(m);
        }
    }
}
