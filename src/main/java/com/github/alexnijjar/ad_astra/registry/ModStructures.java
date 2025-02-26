package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.world.processor.StructureVoidProcessor;
import com.github.alexnijjar.ad_astra.world.structures.LargeJigsawStructure;
import com.mojang.serialization.Codec;

import net.minecraft.structure.StructureType;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.StructureFeature;

public class ModStructures {

    public static StructureType<?> LARGE_JIGSAW_STRUCTURE = register("large_jigsaw_structure", LargeJigsawStructure.CODEC);

    public static final StructureProcessorType<StructureVoidProcessor> STRUCTURE_VOID_PROCESSOR = () -> StructureVoidProcessor.CODEC;

    public static void register() {
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ModIdentifier("structure_void_processor"), STRUCTURE_VOID_PROCESSOR);
    }

    private static <S extends StructureFeature> StructureType<S> register(String id, Codec<S> codec) {
		return Registry.register(Registry.STRUCTURE_TYPE, new ModIdentifier(id), () -> codec);
	}
}