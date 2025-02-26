package com.github.alexnijjar.ad_astra.compat.rei;

import com.github.alexnijjar.ad_astra.compat.rei.coal_generator.CoalGeneratorCategory;
import com.github.alexnijjar.ad_astra.compat.rei.coal_generator.CoalGeneratorDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.compressor.CompressorCategory;
import com.github.alexnijjar.ad_astra.compat.rei.compressor.CompressorDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.cryo_freezer.CryoFreezerConversionCategory;
import com.github.alexnijjar.ad_astra.compat.rei.cryo_freezer.CryoFreezerConversionDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.fuel_conversion.FuelConversionCategory;
import com.github.alexnijjar.ad_astra.compat.rei.fuel_conversion.FuelConversionDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.nasa_workbench.NasaWorkbenchCategory;
import com.github.alexnijjar.ad_astra.compat.rei.nasa_workbench.NasaWorkbenchDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.oxygen_conversion.OxygenConversionCategory;
import com.github.alexnijjar.ad_astra.compat.rei.oxygen_conversion.OxygenConversionDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.space_station.SpaceStationCategory;
import com.github.alexnijjar.ad_astra.compat.rei.space_station.SpaceStationDisplay;
import com.github.alexnijjar.ad_astra.recipes.CompressingRecipe;
import com.github.alexnijjar.ad_astra.recipes.CryoFuelConversionRecipe;
import com.github.alexnijjar.ad_astra.recipes.FuelConversionRecipe;
import com.github.alexnijjar.ad_astra.recipes.GeneratingRecipe;
import com.github.alexnijjar.ad_astra.recipes.NasaWorkbenchRecipe;
import com.github.alexnijjar.ad_astra.recipes.OxygenConversionRecipe;
import com.github.alexnijjar.ad_astra.recipes.SpaceStationRecipe;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.registry.ModRecipes;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class AdAstraClientPlugin implements REIClientPlugin {

	@SuppressWarnings("removal")
	@Override
	public void registerCategories(CategoryRegistry registry) {

		registry.add(new CoalGeneratorCategory());
		registry.add(new CompressorCategory());
		registry.add(new FuelConversionCategory());
		registry.add(new OxygenConversionCategory());
		registry.add(new CryoFreezerConversionCategory());
		registry.add(new NasaWorkbenchCategory());
		registry.add(new SpaceStationCategory());

		registry.addWorkstations(REICategories.COAL_GENERATOR_CATEGORY, EntryStacks.of(ModBlocks.COAL_GENERATOR));
		registry.addWorkstations(REICategories.COMPRESSOR_CATEGORY, EntryStacks.of(ModBlocks.COMPRESSOR));
		registry.addWorkstations(REICategories.FUEL_CONVERSION_CATEGORY, EntryStacks.of(ModBlocks.FUEL_REFINERY));
		registry.addWorkstations(REICategories.OXYGEN_CONVERSION_CATEGORY, EntryStacks.of(ModBlocks.OXYGEN_LOADER), EntryStacks.of(ModBlocks.OXYGEN_DISTRIBUTOR));
		registry.addWorkstations(REICategories.CRYO_FREEZER_CONVERSION_CATEGORY, EntryStacks.of(ModBlocks.CRYO_FREEZER));
		registry.addWorkstations(REICategories.NASA_WORKBENCH_CATEGORY, EntryStacks.of(ModBlocks.NASA_WORKBENCH));
		registry.addWorkstations(REICategories.SPACE_STATION_CATEGORY, EntryStacks.of(ItemStack.EMPTY));

		registry.removePlusButton(REICategories.SPACE_STATION_CATEGORY);
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {

		registry.registerRecipeFiller(GeneratingRecipe.class, ModRecipes.GENERATING_RECIPE, CoalGeneratorDisplay::new);
		registry.registerRecipeFiller(CompressingRecipe.class, ModRecipes.COMPRESSING_RECIPE, CompressorDisplay::new);
		registry.registerRecipeFiller(FuelConversionRecipe.class, ModRecipes.FUEL_CONVERSION_RECIPE, FuelConversionDisplay::new);
		registry.registerRecipeFiller(OxygenConversionRecipe.class, ModRecipes.OXYGEN_CONVERSION_RECIPE, OxygenConversionDisplay::new);
		registry.registerRecipeFiller(CryoFuelConversionRecipe.class, ModRecipes.CRYO_FUEL_CONVERSION_RECIPE, CryoFreezerConversionDisplay::new);
		registry.registerRecipeFiller(NasaWorkbenchRecipe.class, ModRecipes.NASA_WORKBENCH_RECIPE, NasaWorkbenchDisplay::new);
		registry.registerRecipeFiller(SpaceStationRecipe.class, ModRecipes.SPACE_STATION_RECIPE, SpaceStationDisplay::new);

		DefaultInformationDisplay info = DefaultInformationDisplay.createFromEntry(EntryStacks.of(ModItems.OIL_BUCKET), Text.translatable("rei.text.ad_astra.oil.title"));
		info.lines(Text.translatable("rei.text.ad_astra.oil.body"));
		registry.add(info);
	}
}
