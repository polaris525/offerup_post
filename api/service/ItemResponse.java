package com.offerup.postitem.api.service;

public class ItemResponse extends BaseResponse
{
    public class Data
    {
        Item item;

        public Item getItem()
        {
            return this.item;
        }
    }

    Data data;

    public Item getItem()
    {
        if (this.data == null) {
            return null;
        }
        return this.data.getItem();
    }

    public boolean isSuccess()
    {
        if (getItem() == null) {
            return false;
        }
        return super.isSuccess();
    }
}
