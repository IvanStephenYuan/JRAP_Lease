/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jingrui.jrap.activiti.custom;

import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.engine.ActivitiException;
import org.activiti.image.exception.ActivitiImageException;
import org.activiti.image.util.ReflectUtil;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.lang.reflect.Field;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a canvas on which BPMN 2.0 constructs can be drawn.
 * <p>
 * Some of the icons used are licensed under a Creative Commons Attribution 2.5
 * License, see http://www.famfamfam.com/lab/icons/silk/
 *
 * @author Joram Barrez
 */
public class SvgProcessDiagramCanvas extends DefaultProcessDiagramCanvas {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SvgProcessDiagramCanvas.class);

    public SvgProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType, String activityFontName,
                                   String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
        super(width, height, minX, minY, imageType, activityFontName, labelFontName, annotationFontName,
                customClassLoader);
    }

    public void initialize(String imageType) {
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();

        // Create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        this.g = new SVGGraphics2D(document);
        ((SVGGraphics2D) this.g).setSVGCanvasSize(new Dimension(canvasWidth, canvasHeight));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(Color.black);

        Font font = new Font(activityFontName, Font.BOLD, FONT_SIZE);
        g.setFont(font);
        this.fontMetrics = g.getFontMetrics();

        LABEL_FONT = new Font(labelFontName, Font.ITALIC, 10);
        ANNOTATION_FONT = new Font(annotationFontName, Font.PLAIN, FONT_SIZE);

        try {
            USERTASK_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/userTask.png", customClassLoader));
            SCRIPTTASK_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/scriptTask.png", customClassLoader));
            SERVICETASK_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/serviceTask.png", customClassLoader));
            RECEIVETASK_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/receiveTask.png", customClassLoader));
            SENDTASK_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/sendTask.png", customClassLoader));
            MANUALTASK_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/manualTask.png", customClassLoader));
            BUSINESS_RULE_TASK_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/businessRuleTask.png", customClassLoader));
            SHELL_TASK_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/shellTask.png", customClassLoader));
            CAMEL_TASK_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/camelTask.png", customClassLoader));
            MULE_TASK_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/muleTask.png", customClassLoader));

            TIMER_IMAGE = ImageIO.read(ReflectUtil.getResource("org/activiti/icons/timer.png", customClassLoader));
            COMPENSATE_THROW_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/compensate-throw.png", customClassLoader));
            COMPENSATE_CATCH_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/compensate.png", customClassLoader));
            ERROR_THROW_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/error-throw.png", customClassLoader));
            ERROR_CATCH_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/error.png", customClassLoader));
            MESSAGE_THROW_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/message-throw.png", customClassLoader));
            MESSAGE_CATCH_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/message.png", customClassLoader));
            SIGNAL_THROW_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/signal-throw.png", customClassLoader));
            SIGNAL_CATCH_IMAGE = ImageIO
                    .read(ReflectUtil.getResource("org/activiti/icons/signal.png", customClassLoader));
        } catch (IOException e) {
            LOGGER.warn("Could not load image for process diagram creation: {}", e.getMessage());
        }
    }

    /**
     * Generates an image of what currently is drawn on the canvas.
     * <p>
     * called.
     */
    public InputStream generateImage(String imageType) {
        if (closed) {
            throw new ActivitiImageException("ProcessDiagramGenerator already closed");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (Writer writer = new OutputStreamWriter(out, "UTF-8")) {
            ((SVGGraphics2D) g).stream(writer);
        } catch (SVGGraphics2DIOException e) {
            throw new ActivitiException("Unable to generate image", e);
        } catch (UnsupportedEncodingException e) {
            throw new ActivitiException("Unsupported encoding for output stream", e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public void drawLabel(String text, GraphicInfo graphicInfo, boolean centered) {
        float interline = 1.0f;

        // text
        if (text != null && text.length() > 0) {
            Paint originalPaint = g.getPaint();
            Font originalFont = g.getFont();

            g.setPaint(LABEL_COLOR);
            g.setFont(LABEL_FONT);

            int wrapWidth = 100;
            int textY = (int) graphicInfo.getY();

            // TODO: use drawMultilineText()
            AttributedString as = new AttributedString(text);
            as.addAttribute(TextAttribute.FOREGROUND, g.getPaint());
            as.addAttribute(TextAttribute.FONT, g.getFont());
            AttributedCharacterIterator aci = as.getIterator();
            FontRenderContext frc = new FontRenderContext(null, true, false);
            LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);

            while (lbm.getPosition() < text.length()) {
                TextLayout tl = lbm.nextLayout(wrapWidth);
                textY += tl.getAscent();
                Rectangle2D bb = tl.getBounds();
                double tX = graphicInfo.getX();
                if (centered) {
                    tX += (int) (graphicInfo.getWidth() / 2 - bb.getWidth() / 2);
                }
                String str = getStr(tl);
                if (str != null) {
                    // 直接 drawString, 将文字输出到 svg 中
                    g.drawString(str, (int) tX, textY);
                } else
                    tl.draw(g, (float) tX, textY);
                textY += tl.getDescent() + tl.getLeading() + (interline - 1.0f) * tl.getAscent();
            }

            // restore originals
            g.setFont(originalFont);
            g.setPaint(originalPaint);
        }
    }

    protected void drawMultilineText(String text, int x, int y, int boxWidth, int boxHeight, boolean centered) {
        // Create an attributed string based in input text
        AttributedString attributedString = new AttributedString(text);
        attributedString.addAttribute(TextAttribute.FONT, g.getFont());
        attributedString.addAttribute(TextAttribute.FOREGROUND, Color.black);

        AttributedCharacterIterator characterIterator = attributedString.getIterator();

        int currentHeight = 0;
        // Prepare a list of lines of text we'll be drawing
        List<TextLayout> layouts = new ArrayList<TextLayout>();
        String lastLine = null;

        LineBreakMeasurer measurer = new LineBreakMeasurer(characterIterator, g.getFontRenderContext());

        TextLayout layout = null;
        while (measurer.getPosition() < characterIterator.getEndIndex() && currentHeight <= boxHeight) {

            int previousPosition = measurer.getPosition();

            // Request next layout
            layout = measurer.nextLayout(boxWidth);

            int height = ((Float) (layout.getDescent() + layout.getAscent() + layout.getLeading())).intValue();

            if (currentHeight + height > boxHeight) {
                // The line we're about to add should NOT be added anymore,
                // append three dots to previous one instead
                // to indicate more text is truncated
                if (!layouts.isEmpty()) {
                    layouts.remove(layouts.size() - 1);

                    if (lastLine.length() >= 4) {
                        lastLine = lastLine.substring(0, lastLine.length() - 4) + "...";
                    }
                    layouts.add(new TextLayout(lastLine, g.getFont(), g.getFontRenderContext()));
                }
                break;
            } else {
                layouts.add(layout);
                lastLine = text.substring(previousPosition, measurer.getPosition());
                currentHeight += height;
            }
        }

        int currentY = y + (centered ? ((boxHeight - currentHeight) / 2) : 0);
        int currentX = 0;

        // Actually draw the lines
        for (TextLayout textLayout : layouts) {

            currentY += textLayout.getAscent();
            currentX = x + (centered ? ((boxWidth - ((Double) textLayout.getBounds().getWidth()).intValue()) / 2) : 0);

            String str = getStr(textLayout);
            if (str != null) {
                // 直接 drawString, 将文字输出到 svg 中
                g.drawString(str, currentX, currentY);
            } else
                textLayout.draw(g, currentX, currentY);
            currentY += textLayout.getDescent() + textLayout.getLeading();
        }

    }

    private static Field FIELD_textLine = null;
    private static Field FIELD_fChars = null;
    private static Field FIELD_fCharsStart = null;
    private static Field FIELD_fCharsLimit = null;

    private static boolean field_extra = false;

    private static void extraField(Class clazz) {
        if (field_extra) {
            return;
        }
        FIELD_fChars = ReflectionUtils.findField(clazz, "fChars");
        ReflectionUtils.makeAccessible(FIELD_fChars);
        FIELD_fCharsStart = ReflectionUtils.findField(clazz, "fCharsStart");
        ReflectionUtils.makeAccessible(FIELD_fCharsStart);
        FIELD_fCharsLimit = ReflectionUtils.findField(clazz, "fCharsLimit");
        ReflectionUtils.makeAccessible(FIELD_fCharsLimit);
        field_extra = true;
    }

    /**
     * extra text from TextLayout
     *
     * @param tl
     * @return
     */
    protected String getStr(TextLayout tl) {
        try {
            if (FIELD_textLine == null) {
                FIELD_textLine = ReflectionUtils.findField(TextLayout.class, "textLine");
                ReflectionUtils.makeAccessible(FIELD_textLine);
            }
            Object textLine = FIELD_textLine.get(tl);
            extraField(textLine.getClass());
            char[] fChars = (char[]) FIELD_fChars.get(textLine);
            int fCharsStart = FIELD_fCharsStart.getInt(textLine);
            int fCharsLimit = FIELD_fCharsLimit.getInt(textLine);
            return new String(fChars, fCharsStart, fCharsLimit - fCharsStart);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
