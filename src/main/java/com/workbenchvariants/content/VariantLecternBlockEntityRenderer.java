package com.workbenchvariants.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;

public class VariantLecternBlockEntityRenderer implements BlockEntityRenderer<VariantLecternBlockEntity> {
    private final BookModel bookModel;

    public VariantLecternBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.bookModel = new BookModel(context.bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public void render(
            VariantLecternBlockEntity blockEntity,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
    ) {
        BlockState blockState = blockEntity.getBlockState();

        if (blockState.getValue(LecternBlock.HAS_BOOK)) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.0625F, 0.5F);

            float yRot = blockState.getValue(LecternBlock.FACING).getClockWise().toYRot();
            poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
            poseStack.mulPose(Axis.ZP.rotationDegrees(67.5F));
            poseStack.translate(0.0F, -0.125F, 0.0F);

            this.bookModel.setupAnim(0.0F, 0.1F, 0.9F, 1.2F);

            VertexConsumer vertexConsumer =
                    EnchantTableRenderer.BOOK_LOCATION.buffer(bufferSource, RenderType::entitySolid);

            this.bookModel.render(
                    poseStack,
                    vertexConsumer,
                    packedLight,
                    packedOverlay,
                    1.0F,
                    1.0F,
                    1.0F,
                    1.0F
            );

            poseStack.popPose();
        }
    }
}