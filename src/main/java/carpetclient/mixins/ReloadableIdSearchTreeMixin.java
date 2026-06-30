package carpetclient.mixins;

import carpetclient.mixinInterface.AMixinSearchTree;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.search.ReloadableIdSearchTree;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

/**
 * Search tree cleaning, for custom crafting
 */
@Mixin(ReloadableIdSearchTree.class)
public abstract class ReloadableIdSearchTreeMixin implements AMixinSearchTree {
	@Shadow
	private @Final List<?> contents;

	@Shadow
	private Object2IntMap<?> ordered;

	@Override
	public void clear() {
		this.ordered.clear();
		this.contents.clear();
	}
}
