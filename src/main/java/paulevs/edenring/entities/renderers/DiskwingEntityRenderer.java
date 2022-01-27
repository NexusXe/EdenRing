package paulevs.edenring.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import paulevs.edenring.entities.DiskwingEntity;
import paulevs.edenring.entities.DiskwingEntity.DiskwingType;
import paulevs.edenring.entities.models.DiskwingEntityModel;
import paulevs.edenring.registries.EdenEntitiesRenderers;

public class DiskwingEntityRenderer extends MobRenderer<DiskwingEntity, DiskwingEntityModel> {
	private static final RenderType[] GLOW = new RenderType[DiskwingType.VALUES.length];
	
	public DiskwingEntityRenderer(Context ctx) {
		super(ctx, new DiskwingEntityModel(ctx.bakeLayer(EdenEntitiesRenderers.DISKWING_MODEL)), 0.5F);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public RenderType renderType() {
				return GLOW[0];
			}
			
			@Override
			public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, DiskwingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
				int variant = entity.getVariant().ordinal();
				VertexConsumer vertexConsumer = vertexConsumers.getBuffer(GLOW[variant]);
				this.getParentModel().renderToBuffer(matrices, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
		});
	}
	
	@Override
	public ResourceLocation getTextureLocation(DiskwingEntity entity) {
		return entity.getVariant().getTexture();
	}
	
	static {
		final int length = DiskwingType.VALUES.length;
		for (byte i = 0; i < length; i++) {
			GLOW[i] = RenderType.eyes(DiskwingType.VALUES[i].getGlow());
		}
	}
}
