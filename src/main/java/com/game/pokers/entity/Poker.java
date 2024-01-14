package com.game.pokers.entity;

import com.game.pokers.constant.PokerColor;
import com.game.pokers.constant.PokerIndex;

public class Poker {

    public int pokerId;

    public int getPokerId() {
        return pokerId;
    }

    public void setPokerId(int pokerId) {
        this.pokerId = pokerId;
    }

    public PokerColor GetColor() {
        return PokerColor.getColor(pokerId % 4);
    }

    public PokerIndex GetIndex() {
        return PokerIndex.getIndex(pokerId % 4);
    }

    public String GetView() {
        return this.GetIndex().getView() + this.GetColor().getView();
    }
}
