package com.github.alexnijjar.ad_astra.blocks.launchpad;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class LaunchPadBlockEntity extends BlockEntity {

    public LaunchPadBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.LAUNCH_PAD, blockPos, blockState);
    }

    public void tick() {
    }
}
