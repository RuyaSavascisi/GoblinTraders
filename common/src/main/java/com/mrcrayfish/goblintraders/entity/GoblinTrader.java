package com.mrcrayfish.goblintraders.entity;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.Constants;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.trades.EntityTrades;
import com.mrcrayfish.goblintraders.trades.IRaritySettings;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class GoblinTrader extends AbstractGoblinEntity
{
    public GoblinTrader(Level level)
    {
        super(ModEntities.GOBLIN_TRADER.get(), level);
    }

    @Override
    public ResourceLocation getTexture()
    {
        return new ResourceLocation(Constants.MOD_ID, "textures/entity/goblin_trader.png");
    }

    @Override
    protected void populateTradeData()
    {
        MerchantOffers offers = this.getOffers();
        EntityTrades entityTrades = TradeManager.instance().getTrades(ModEntities.GOBLIN_TRADER.get());
        if(entityTrades != null)
        {
            Map<TradeRarity, List<VillagerTrades.ItemListing>> tradeMap = entityTrades.map();
            for(TradeRarity rarity : TradeRarity.values())
            {
                IRaritySettings settings = Config.ENTITIES.goblinTrader.trades.getSettings(rarity);
                if(settings.includeChance() <= 0.0)
                    continue;
                if(settings.includeChance() < 1.0 && this.getRandom().nextDouble() > settings.includeChance())
                    continue;
                List<VillagerTrades.ItemListing> trades = tradeMap.get(rarity);
                int min = Math.min(settings.getMinValue(), settings.getMaxValue());
                int max = Math.max(settings.getMinValue(), settings.getMaxValue());
                int count = min + this.getRandom().nextInt(max - min + 1);
                this.addTrades(offers, trades, count, rarity.shouldShuffle());
            }
        }
    }

    @Override
    public ItemStack getFavouriteFood()
    {
        return new ItemStack(Items.APPLE);
    }

    @Override
    protected int getMaxRestockDelay()
    {
        return Config.ENTITIES.goblinTrader.restockDelay.get();
    }

    @Override
    public boolean canAttackBack()
    {
        return Config.ENTITIES.goblinTrader.canAttackBack.get();
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return Config.ENTITIES.goblinTrader.gruntNoiseInterval.get();
    }
}
