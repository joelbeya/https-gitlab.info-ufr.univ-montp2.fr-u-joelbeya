package com.example.easycourse.final_dummy;


import com.example.easycourse.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

//    private static final int COUNT = 10;

    static {
        addItem(new DummyItem("1", R.drawable.mat, "Isaac Newton", "2 minutes ago",
                "Bacon ipsum dolor sit amet"));
        addItem(new DummyItem("2", R.drawable.mat, "Margaret Thatcher", "18 minutes ago",
                "Tri-tip spare ribs pancetta t-bone short ribs meatball"));
        addItem(new DummyItem("3", R.drawable.mat, "Vincent Van Gogh", "5 hours ago",
                "Strip steak ground round doner shankle"));
        addItem(new DummyItem("4", R.drawable.mat, "Tom Brokaw", "1 day ago",
                "Corned beef drumstick tri-tip flank ribeye tongue"));
        addItem(new DummyItem("5", R.drawable.mat, "Thomas Edison", "1 day ago",
                "Pastrami ham hock short ribs turkey boudin hamburger bresaola shank"));
        addItem(new DummyItem("6", R.drawable.mat, "Edgar Allen Poe", "2 days ago",
                "Spare ribs drumstick kielbasa, ground round ball tip pork shoulder short ribs"));
        addItem(new DummyItem("7", R.drawable.mat, "Plato", "2 days ago",
                "Shankle venison shank strip"));
        addItem(new DummyItem("8", R.drawable.mat, "Robin Williams", "4 days ago",
                "Steak tongue; pig shank t-bone, sausage hamburger ham venison"));
        addItem(new DummyItem("9", R.drawable.mat, "Jacqueline Kennedy Onasis", "4 days ago",
                "Shankle venison shank strip"));
        addItem(new DummyItem("10", R.drawable.mat, "Pablo Piccaso", "7 days ago",
                "Ribeye tri-tip biltong swine short loin pork belly hamburger"));
        addItem(new DummyItem("11", R.drawable.mat, "Ernest Hemingway", "8 days ago",
                "Biltong ball tip spare ribs rump sausage t-bone turkey pork belly jowl beef"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
//
//    private static DummyItem createDummyItem(int position) {
//        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }
//
//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String title;
        public String author;
        public String time;
        public String content;
        public int photoResId;

        public DummyItem(String id, int photoResId, String author, String time, String title) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.time = time;
            this.photoResId = photoResId;
            this.content =
                    "Bacon ipsum dolor sit amet <a href='foo.html'>frankfurter tenderloin</a> beef ribs pig turducken,"
                            + " tail jowl cow bresaola pork shoulder pastrami short ribs drumstick"
                            + " strip steak.<br>"
                            + "<br>"
                            + "Beef ribs kielbasa sirloin pork loin chicken pork chop"
                            + " rump andouille tail. Beef ribs corned beef sausage, doner shoulder"
                            + " capicola pork pastrami jowl chuck shankle. T-bone ribeye chicken"
                            + " turducken drumstick rump prosciutto tri-tip pork belly sausage"
                            + " shankle venison shoulder pastrami ball tip.<br>"
                            + "<br>"
                            + "Frankfurter ball tip pork belly shoulder short loin. Boudin"
                            + " andouille ham hock tri-tip tail, capicola t-bone fatback kielbasa"
                            + " venison cow drumstick ribeye biltong. Shoulder ribeye hamburger,"
                            + " pork belly strip steak chuck spare ribs ham hock salami. Turkey"
                            + " filet mignon t-bone, ribeye tail boudin jowl short loin andouille"
                            + " spare ribs. Cow tri-tip ball tip chuck, leberkas venison meatball"
                            + " pastrami salami short loin bresaola. Turducken sirloin turkey"
                            + " ribeye bresaola jowl bacon meatloaf sausage.<br>"
                            + "<br>"
                            + "Brisket doner tail capicola. Ham swine biltong jowl ribeye jerky"
                            + " tenderloin pork belly hamburger venison brisket. Capicola ground"
                            + " round pancetta jowl, turducken pork belly doner venison spare"
                            + " ribs boudin frankfurter. Cow swine ball tip jowl, hamburger salami"
                            + " prosciutto biltong ribeye venison tail short loin chuck turkey"
                            + ". Leberkas fatback tongue, shoulder prosciutto strip steak ground"
                            + " round short ribs kielbasa short loin flank. Meatball drumstick"
                            + " turkey pork loin. Cow spare ribs chuck, beef ribs tongue ham salami"
                            + " swine drumstick capicola jowl sirloin pork bresaola.";
        }
    }
}
