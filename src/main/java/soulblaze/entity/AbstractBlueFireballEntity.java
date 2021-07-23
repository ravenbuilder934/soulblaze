package soulblaze.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import soulblaze.item.ModItems;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)

public abstract class AbstractBlueFireballEntity extends AbstractFireballEntity implements IRendersAsItem
{
    private static final DataParameter<ItemStack> DATA_ITEM_STACK = EntityDataManager.defineId(AbstractBlueFireballEntity.class, DataSerializers.ITEM_STACK);

    public AbstractBlueFireballEntity(EntityType<? extends AbstractBlueFireballEntity> p_i50166_1_, World p_i50166_2_)
    {
        super(p_i50166_1_, p_i50166_2_);
    }

    public AbstractBlueFireballEntity(EntityType<? extends AbstractBlueFireballEntity> p_i50167_1_, double p_i50167_2_, double p_i50167_4_, double p_i50167_6_, double p_i50167_8_, double p_i50167_10_, double p_i50167_12_, World p_i50167_14_)
    {
        super(p_i50167_1_, p_i50167_2_, p_i50167_4_, p_i50167_6_, p_i50167_8_, p_i50167_10_, p_i50167_12_, p_i50167_14_);
    }

    public AbstractBlueFireballEntity(EntityType<? extends AbstractBlueFireballEntity> p_i50168_1_, LivingEntity p_i50168_2_, double p_i50168_3_, double p_i50168_5_, double p_i50168_7_, World p_i50168_9_)
    {
        super(p_i50168_1_, p_i50168_2_, p_i50168_3_, p_i50168_5_, p_i50168_7_, p_i50168_9_);
    }

    @Override
    protected ItemStack getItemRaw()
    {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack getItem()
    {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(ModItems.SOUL_FIRE_CHARGE.get()) : itemstack;
    }

    @Override
    public void setItem(ItemStack p_213898_1_)
    {
        if (p_213898_1_.getItem() != ModItems.SOUL_FIRE_CHARGE.get() || p_213898_1_.hasTag())
        {
            this.getEntityData().set(DATA_ITEM_STACK, Util.make(p_213898_1_.copy(), (p_213897_0_) ->
                    p_213897_0_.setCount(1)));
        }

    }

    @Override
    protected void defineSynchedData()
    {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT p_213281_1_)
    {
        super.addAdditionalSaveData(p_213281_1_);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty())
        {
            p_213281_1_.put("Item", itemstack.save(new CompoundNBT()));
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundNBT p_70037_1_)
    {
        super.readAdditionalSaveData(p_70037_1_);
        ItemStack itemstack = ItemStack.of(p_70037_1_.getCompound("Item"));
        this.setItem(itemstack);
    }
}