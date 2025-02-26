package com.github.alexnijjar.ad_astra.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.mixin.PaintingEntityInvoker;
import com.github.alexnijjar.ad_astra.registry.ModEntityTypes;
import com.github.alexnijjar.ad_astra.registry.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class SpacePaintingEntity extends PaintingEntity {

	public SpacePaintingEntity(EntityType<? extends PaintingEntity> entityType, World world) {
		super(entityType, world);
	}

	protected SpacePaintingEntity(World world, BlockPos pos) {
		this(ModEntityTypes.SPACE_PAINTING, world);
		this.attachmentPos = pos;
	}

	public static Optional<SpacePaintingEntity> placeSpacePainting(World world, BlockPos pos, Direction facing) {
		SpacePaintingEntity paintingEntity = new SpacePaintingEntity(world, pos);

		List<Holder<PaintingVariant>> spacePaintings = getSpacePaintings();

		if (spacePaintings.isEmpty()) {
			return Optional.empty();
		}

		paintingEntity.setFacing(facing);
		spacePaintings.removeIf(variant -> {
			((PaintingEntityInvoker) paintingEntity).adastra_invokeSetVariant(variant);
			return !paintingEntity.canStayAttached();
		});

		if (spacePaintings.isEmpty()) {
			return Optional.empty();
		}

		int i = spacePaintings.stream().mapToInt(SpacePaintingEntity::getSize).max().orElse(0);
		spacePaintings.removeIf(variant -> SpacePaintingEntity.getSize(variant) < i);
		Optional<Holder<PaintingVariant>> optional = Util.getRandomOrEmpty(spacePaintings, paintingEntity.random);

		if (optional.isEmpty()) {
			return Optional.empty();
		}

		((PaintingEntityInvoker) paintingEntity).adastra_invokeSetVariant(optional.get());
		paintingEntity.setFacing(facing);
		return Optional.of(paintingEntity);
	}

	protected static int getSize(Holder<PaintingVariant> variant) {
		return variant.value().getWidth() * variant.value().getHeight();
	}

	public static List<Holder<PaintingVariant>> getSpacePaintings() {
		List<Holder<PaintingVariant>> paintings = new ArrayList<>();
		Registry.PAINTING_VARIANT.forEach(painting -> {
			if (Registry.PAINTING_VARIANT.getId(painting).getNamespace().equals(AdAstra.MOD_ID)) {
				paintings.add(Holder.createDirect(painting));
			}
		});

		return paintings;
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {

		super.readCustomDataFromNbt(nbt);
		RegistryKey<PaintingVariant> registryKey = RegistryKey.of(Registry.PAINTING_VARIANT_KEY, new Identifier(nbt.getString("variant")));
		((PaintingEntityInvoker) this).adastra_invokeSetVariant(Registry.PAINTING_VARIANT.getHolder(registryKey).get());
		this.facing = Direction.fromHorizontal(nbt.getByte("facing"));
		this.setFacing(this.facing);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString("variant", Registry.PAINTING_VARIANT.getId(this.getVariant().value()).toString());
		nbt.putByte("facing", (byte) this.facing.getHorizontal());
	}

	@Override
	public ItemEntity dropItem(ItemConvertible item) {
		return super.dropItem(ModItems.SPACE_PAINTING);
	}

	@Override
	public ItemStack getPickBlockStack() {
		return new ItemStack(ModItems.SPACE_PAINTING);
	}
}