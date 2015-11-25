package ca.ualberta.cs.xpertsapp.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Trade;

/**
 * Created by kmbaker on 11/24/15.
 */
public class TradeListAdapter extends BaseAdapter {


    public List<Trade> tradeData;
    private Context context;
    private LayoutInflater inflater;

    /**
     * Contructor for this Adapter
     * @param trades The list of trades to create an adapter for
     */
    public TradeListAdapter(Context context, List<Trade> trades) {
        this.context = context;
        this.tradeData = trades;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Updates the ListView with a new list of trades
     * @param trades the new list of trades
     */
    public void updateTradeList(List<Trade> trades) {
        tradeData.clear();
        tradeData.addAll(trades);
        this.notifyDataSetChanged();
    }

    /**
     * @return  Number of trades in the adapter
     */
    @Override
    public int getCount() {
        return tradeData.size();
    }

    /**
     * @param position of a trade in the adapter
     * @return The trade belonging to the ListItem in the given position
     */
    @Override
    public Trade getItem(int position) {
        return tradeData.get(position);
    }

    /**
     * @param position of an item
     * @return the position of the item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Lays out the view of a list item representing a trade.
     * @param position of an item in the trade list
     * @param tradeListItem to layout the view for
     * @return the view for the given service list item
     */
    @Override
    public View getView(int position, View tradeListItem, ViewGroup parent) {
        View vi = tradeListItem;
        if (vi == null)
            vi = inflater.inflate(R.layout.trade_list_item, null);
        Trade trade = tradeData.get(position);
        TextView borrower = (TextView) vi.findViewById(R.id.trade_list_borrower);
        TextView service = (TextView) vi.findViewById(R.id.trade_list_service);
        TextView category = (TextView) vi.findViewById(R.id.trade_list_category);
        TextView state = (TextView) vi.findViewById(R.id.trade_list_state);

        borrower.setText(trade.getBorrower().getName());
        service.setText(trade.getOwnerServices().get(0).getName());
        category.setText(trade.getOwnerServices().get(0).getCategory().toString());
        //state.setText(trade.getState().getString());
        return vi;
    }
}
