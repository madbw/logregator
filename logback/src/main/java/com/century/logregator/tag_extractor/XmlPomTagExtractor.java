package com.century.logregator.tag_extractor;

import com.century.logregator.model.MvnTag;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class XmlPomTagExtractor {
    public static MvnTag extract(String pom) {
        if (pom == null) {
            throw new NullPointerException("pom cant be null");
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputStream inputStream = new ByteArrayInputStream(pom.getBytes());
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            MvnTag tag = parseProject(nodeList);
            return tag;
        } catch (ParserConfigurationException e) {
            log.error(e.getMessage(), e);
            throw new PomParseException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PomParseException(e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new PomParseException(e);
        }
    }

    private static MvnTag parseParent(Node node) {
        String artifactId = "";
        String version = "";
        String groupId = "";
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node cNode = childNodes.item(i);
            switch (cNode.getNodeName()) {
                case "artifactId":
                    artifactId = cNode.getLastChild().getTextContent().trim();
                    break;
                case "groupId":
                    groupId = cNode.getLastChild().getTextContent().trim();
                    break;
                case "version":
                    version = cNode.getLastChild().getTextContent().trim();
                    break;

            }
        }
        return new MvnTag(groupId, artifactId, version);
    }

    private static MvnTag parseProject(NodeList nodeList) {
        MvnTag parent = null;
        String artifactId = "";
        String version = "";
        String groupId = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if ("parent".equals(node.getNodeName())) {
                parent = parseParent(node);
                continue;
            }
            switch (node.getNodeName()) {
                case "artifactId":
                    artifactId = node.getLastChild().getTextContent().trim();
                    break;
                case "groupId":
                    groupId = node.getLastChild().getTextContent().trim();
                    break;
                case "version":
                    version = node.getLastChild().getTextContent().trim();
                    break;
            }
        }
        if ((artifactId.isEmpty() || version.isEmpty() || groupId.isEmpty()) && parent == null) {
            throw new WrongPomFileException("Missing parent tag in pom file, missing some atributes");
        }
        if (artifactId.isEmpty()) {
            artifactId = parent.getArtifactId();
        }
        if (version.isEmpty()) {
            version = parent.getVersion();
        }
        if (groupId.isEmpty()) {
            groupId = parent.getGroupId();
        }

        return new MvnTag(groupId, artifactId, version);
    }
}
