package com.addie.toasted;

import com.addie.toasted.core.ToastedModBlocks;
import com.addie.toasted.core.ToastedModItemGroups;
import com.addie.toasted.core.ToastedModItems;
import com.addie.toasted.core.ToastedModSounds;
import dev.amble.lib.container.RegistryContainer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToastedMod implements ModInitializer {
	public static final String MOD_ID = "toasted";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

        RegistryContainer.register(ToastedModItemGroups.class, MOD_ID);
        RegistryContainer.register(ToastedModItems.class, MOD_ID);
        RegistryContainer.register(ToastedModBlocks.class, MOD_ID);
        RegistryContainer.register(ToastedModSounds.class, MOD_ID);

	}
    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

}