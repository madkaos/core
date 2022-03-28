package com.madkaos.core.gui;


import java.util.ArrayList;
import java.util.List;

import com.madkaos.core.math.Vector2;
import com.madkaos.core.utils.InventoryUtils;

public abstract class InventoryArea<S> {
    private final InventoryGUIContext context;
    private final ItemBuilder fill;
    private final List<S> items;
    private final List<Integer> slots;

    public InventoryArea (final InventoryGUIContext context, final ItemBuilder fill, final int x1, final int y1, final int x2, final int y2) {
        this.context = context;
        this.fill = fill;
        this.items = new ArrayList<>();
        this.slots = new ArrayList<>();

        for (int i = 0; i <= 63; i++) {
            final Vector2 slot = InventoryUtils.getXYBySlot(i);
            final int slotX = (int) slot.getX();
            final int slotY = (int) slot.getY();

            if (slotX >= x1 && slotX <= x2) {
                if (slotY >= y1 && slotY <= y2) {
                    this.slots.add(i);
                }
            }
        }
    }

    public InventoryArea<S> addItem (final S item) {
        this.items.add(item);
        return this;
    }

    public InventoryArea<S> addItems (final List<S> items) {
        for (final S item : items) {
            this.addItem(item);
        }
        return this;
    }

    public abstract boolean filter (final S item);

    public abstract ItemBuilder buildItem (final S item);

    public abstract void onClickItem (final S item);

    public void clear () {
        for (final int slot : this.slots) {
            this.context.clearSlot(slot);
        }
        
        this.context.refresh();
    }

    public void build () {
        int index = 0;

        for (final S item : this.items) {
            if (this.filter(item)) {
                final int slot = this.slots.get(index);
                final ItemBuilder itemBuilder = this.buildItem(item);

                if (itemBuilder == null) {
                    continue;
                }

                itemBuilder.setSlot(slot);
                itemBuilder.setLeftClickAction(() -> {
                    this.onClickItem(item);
                });
                this.context.addItem(itemBuilder);
                index++;
            }
        }

        while (index < this.slots.size()) {
            final int slot = this.slots.get(index);
            this.context.addItem(this.fill.clone().setSlot(slot));
            index++;
        }

        this.context.refresh();
    }
}
