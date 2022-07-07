package xyz.bluspring.randomizedskyblock.mixin;

import net.minecraft.client.gui.screen.world.MoreOptionsDialog;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.bluspring.randomizedskyblock.RandomizedSkyblock;

import java.util.List;
import java.util.Optional;

@Mixin(MoreOptionsDialog.class)
public class MoreOptionsDialogMixin {
    @Inject(at = @At("RETURN"), method = "collectPresets", cancellable = true)
    private static void addVoidGeneratorPreset(Registry<WorldPreset> presetRegistry, TagKey<WorldPreset> tag, CallbackInfoReturnable<Optional<List<RegistryEntry<WorldPreset>>>> cir) {
        var list = cir.getReturnValue();

        cir.setReturnValue(
                list.map(entryList -> {
                    entryList.add(presetRegistry.getOrCreateEntry(RandomizedSkyblock.Companion.getRandomizedSkyblockWorld()));

                    return entryList;
                })
        );
    }
}
