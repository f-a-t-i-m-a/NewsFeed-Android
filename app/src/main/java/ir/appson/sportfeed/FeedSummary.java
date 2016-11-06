package ir.appson.sportfeed;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "FeedSummary")
public class FeedSummary {
    @Element(name = "FeedSummary")
    String name;
}
