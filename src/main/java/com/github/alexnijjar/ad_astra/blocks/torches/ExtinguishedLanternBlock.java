package com.github.alexnijjar.ad_astra.blocks.torches;

import com.github.alexnijjar.ad_astra.util.entity.OxygenUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExtinguishedLanternBlock extends LanternBlock {

	public ExtinguishedLanternBlock(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			ItemStack itemstack = player.getStackInHand(hand);

			if (OxygenUtils.posHasOxygen(world, pos)) {
				if (itemstack.getItem() instanceof FlintAndSteelItem || itemstack.getItem() instanceof FireChargeItem) {

					world.setBlockState(pos, Blocks.LANTERN.getStateWithProperties(state), 3);

					itemstack.getItem().use(world, player, hand);

					boolean hasFlint = itemstack.getItem() instanceof FlintAndSteelItem;

					if (hasFlint) {
						world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, 1);
					} else {
						world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1, 1);
					}

					if (!player.isCreative()) {
						if (hasFlint) {
							itemstack.damage(1, world.random, (ServerPlayerEntity) player);
						} else {
							itemstack.decrement(1);
						}
					}
				}
			}
		}
		return ActionResult.SUCCESS;
	}
}