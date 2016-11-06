package ir.appson.sportfeed;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by fatemeh on 11/6/2016.
 */

@Root(name = "Feeds")
public class Feeds {
    @ElementList(inline = true)
    List<FeedSummary> foodList;
}

