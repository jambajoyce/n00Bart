package edu.berkeley.cs160.JoyceLiu.proj3;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class ETAParser {
    // We don't use namespaces
    private static final String ns = null;
   
    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList<Entry>();

        parser.require(XmlPullParser.START_TAG, ns, "root");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("station")) {
                entries.add(readEntry(parser));
            } 
        }  
        return entries;
    }

    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "station");
        String statName = null;
        String desti1 = null;
        String desti2 = null;
        String desti3 = null;
        String desti4 = null;
        String desti5 = null;
        String etas1 = null;
        String etas2 = null;
        String etas3 = null;
        String etas4 = null;
        String etas5 = null;
        int counter = 0;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            Log.d("parser.getName()", name);
            if (name.equals("name")) {
                statName = readName(parser);
                continue;
            } else if (name.equals("destination") && counter == 0) {
                desti1 = readDesti(parser);
                Log.d("desti1", desti1);
                continue;
            } else if (name.equals("estimate") && counter == 0) {
                etas1 = readETA(parser);
                counter += 1;
                continue;
            } else if (name.equals("destination") && counter == 1) {
                desti2 = readDesti(parser);
                continue;
            } else if (name.equals("estimate") && counter == 1) {
                etas2 = readETA(parser);
                counter += 1;
                continue;
            } else if (name.equals("destination") && counter == 2) {
                desti3 = readDesti(parser);
                continue;
            } else if (name.equals("estimate") && counter == 2) {
                etas3 = readETA(parser);
                counter += 1;
                continue;
            } else if (name.equals("destination") && counter == 3) {
                desti4 = readDesti(parser);
                continue;
            } else if (name.equals("estimate") && counter == 3) {
                etas4 = readETA(parser);
                counter += 1;
                continue;
            } else if (name.equals("destination") && counter == 4) {
                desti5 = readDesti(parser);
                continue;
            } else if (name.equals("estimate") && counter == 4) {
                etas5 = readETA(parser);
                counter += 1;
            }
        }
        return new Entry(statName, desti1, etas1, desti2, etas2, desti3, etas3, desti4, etas4, desti5, etas5 );
    }

    // Processes title tags in the feed.
    private String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "name");
        String n = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "name");
        return n;
    }

    private String readDesti(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "destination");
        String d = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "destination");
        return d;
    }

    private String readETA(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "estimate");
        String e = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "estimate");
        return e;
    }

// For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


        
}

