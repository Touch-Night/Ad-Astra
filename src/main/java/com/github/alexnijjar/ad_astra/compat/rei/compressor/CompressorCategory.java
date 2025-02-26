package com.github.alexnijjar.ad_astra.compat.rei.compressor;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.compat.rei.REICategories;
import com.github.alexnijjar.ad_astra.compat.rei.widgets.EnergyBarWidget;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CompressorCategory implements DisplayCategory<CompressorDisplay> {

	@Override
	public Renderer getIcon() {
		return EntryStacks.of(ModBlocks.COMPRESSOR.asItem());
	}

	@Override
	public Text getTitle() {
		return Text.translatable(ModBlocks.COMPRESSOR.getTranslationKey());
	}

	@Override
	public int getDisplayWidth(CompressorDisplay display) {
		return 144;
	}

	@Override
	public int getDisplayHeight() {
		return 90;
	}

	@Override
	public CategoryIdentifier<? extends CompressorDisplay> getCategoryIdentifier() {
		return REICategories.COMPRESSOR_CATEGORY;
	}

	@Override
	public List<Widget> setupDisplay(CompressorDisplay display, Rectangle bounds) {
		Point startPoint = new Point(bounds.getCenterX() - 60, bounds.getCenterY() - 20);

		List<Widget> widgets = new ArrayList<>();
		List<EntryIngredient> inputs = display.getInputEntries();
		List<EntryIngredient> outputs = display.getOutputEntries();

		widgets.add(Widgets.createRecipeBase(bounds));
		widgets.add(Widgets.createSlot(startPoint).entries(inputs.get(0)).markInput());

		widgets.add(Widgets.createArrow(new Point(startPoint.x + 21, startPoint.y - 0)).animationDurationTicks(display.recipe().getCookTime()));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 50, startPoint.y - 0)).entries(outputs.get(0)).markOutput());

		Widget widget = new EnergyBarWidget(new Point(startPoint.x + 110, startPoint.y - 13), false).animationDurationTicks(150);
		widgets.add(widget);
		widgets.add(Widgets.withTooltip(Widgets.withBounds(widget, bounds), Text.translatable("rei.tooltip.ad_astra.energy_using", AdAstra.CONFIG.compressor.energyPerTick)));

		Text ratioText = Text.translatable("rei.text.ad_astra.seconds", display.recipe().getCookTime() / 20);
		widgets.add(Widgets.createLabel(new Point(startPoint.x + 60, startPoint.y + 45), ratioText).centered().noShadow().color(0xFF404040, 0xFFBBBBBB));

		return widgets;
	}
}