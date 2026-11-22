package com.example.chemtable.client;

import com.example.chemtable.entity.ReactionGrenadeEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class ReactionGrenadeRenderer extends EntityRenderer<ReactionGrenadeEntity> {
    private final ItemRenderer itemRenderer;
    public ReactionGrenadeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }
    @Override public Identifier getTexture(ReactionGrenadeEntity e) {
        return new Identifier("minecraft", "textures/item/glass_bottle.png");
    }
    @Override
    public void render(ReactionGrenadeEntity e, float yaw, float tickDelta, MatrixStack m,
                       VertexConsumerProvider vcp, int light) {
        m.push();
        m.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(e.age * 12));
        ItemStack stack = e.getStack();
        if (stack.isEmpty()) stack = new ItemStack(net.minecraft.item.Items.GLASS_BOTTLE);
        itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, light,
                net.minecraft.client.render.OverlayTexture.DEFAULT_UV, m, vcp, e.getWorld(), e.getId());
        m.pop();
    }
}
