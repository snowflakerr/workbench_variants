package com.workbenchvariants.client;

import com.workbenchvariants.WorkbenchVariants;
import com.workbenchvariants.content.VariantLecternBlockEntityRenderer;
import com.workbenchvariants.registry.ModBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WorkbenchVariants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.LECTERN_VARIANT.get(),
                VariantLecternBlockEntityRenderer::new
        );
    }
}