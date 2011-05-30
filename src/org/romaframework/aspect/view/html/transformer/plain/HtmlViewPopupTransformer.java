package org.romaframework.aspect.view.html.transformer.plain;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.romaframework.aspect.i18n.I18NHelper;
import org.romaframework.aspect.view.ViewAspect;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.area.HtmlViewBinder;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.area.HtmlViewScreenArea;
import org.romaframework.aspect.view.html.area.HtmlViewScreenAreaInstance;
import org.romaframework.aspect.view.html.binder.NullBinder;
import org.romaframework.aspect.view.html.component.HtmlViewContentForm;
import org.romaframework.aspect.view.html.css.CssConstants;
import org.romaframework.aspect.view.html.css.StyleBuffer;
import org.romaframework.aspect.view.html.transformer.AbstractHtmlViewTransformer;
import org.romaframework.aspect.view.html.transformer.Transformer;
import org.romaframework.aspect.view.html.transformer.helper.TransformerHelper;
import org.romaframework.aspect.view.screen.Screen;
import org.romaframework.core.schema.SchemaObject;
import org.romaframework.web.session.HttpAbstractSessionAspect;

public class HtmlViewPopupTransformer extends AbstractHtmlViewTransformer implements Transformer {

	public static final String	CLOSE_POPUP_EVENT_NAME	= "ClosePopup";
	public static final String	superscreenID						= "#screen_main_popups";
	public static final String	popup										= "table.popup";
	public static final String	popname									= ".header";
	public static final String	jqResize								= ".jqResize";

	private static final String	HEIGHT									= "23px";
	private static final String	ZERO										= "0";
	private static final String	Z_INDEX									= "1000";
	private static final String	SEPARATOR								= TransformerHelper.SEPARATOR;

	private static String row(final int row) {
		return popup + " tr.popup_row_" + row;
	}

	private static String col(final int col) {
		return popup + " td.popup_col_" + col;
	}

	private static String element(final int row, final int col) {
		return popup + " tr.popup_row_" + row + " td.popup_col_" + col;
	}

	public HtmlViewBinder getBinder(HtmlViewRenderable renderable) {
		return NullBinder.getInstance();
	}

	public void transformPart(final HtmlViewRenderable component, final String part, OutputStream out) throws IOException {

		

		out.write(("<table cellpadding=\"0\" cellspacing=\"0\" class=\"jqDnr " + helper.getHtmlClass(this, null, null) + "\" id=\""
				+ helper.getHtmlId(component, null) + "\">\n").getBytes());
		out.write(("<tr class=\"popup_row_1\">\n").getBytes());
		out.write(("<td class=\"popup_col_1\"></td>\n").getBytes());
		out.write(("<td class=\"popup_col_2\"></td>\n").getBytes());
		out.write(("<td class=\"popup_col_3\"></td>\n").getBytes());
		out.write(("</tr>\n").getBytes());
		out.write(("<tr class=\"popup_row_2\">\n").getBytes());
		out.write(("<td class=\"popup_col_1 popup_left_border\"></td>\n").getBytes());
		out.write(("<td class=\"popup_col_2\">\n").getBytes());
		out.write(("<div class=\"header jqHandle\">\n").getBytes());

		// This is the where the popup is binded

		out.write(("<input class=\"" + helper.getHtmlClass(this, "close", null) + "\" id=\"" + helper.getHtmlId(component, "close")
				+ "\" type=\"button\" value=\"\" name=\"(PojoEvent)_" + component.getId() + SEPARATOR + "close" + SEPARATOR
				+ ((HtmlViewScreenArea) component).getName() + "\" onclick=\"romaEvent('" + component.getId() + "', '"
				+ CLOSE_POPUP_EVENT_NAME + "')\"/>\n").getBytes());

		String simpleName = getPopupLabel(component);

		out.write(("<div class=\"jqDrag\">" + simpleName + "</div>").getBytes());
		out.write(("</div>").getBytes());

		final HtmlViewScreenArea area = (HtmlViewScreenArea) component;
		final HtmlViewContentForm form = area.getForm();
		try {
			HtmlViewAspectHelper.renderByJsp(form, HtmlViewAspectHelper.getServletRequest(), out);
		} catch (Exception e) {
			e.printStackTrace();// TODO handle exception!!!
		}
		out.write(("</td>\n").getBytes());
		out.write(("<td class=\"popup_col_3 popup_right_border\"></td>\n").getBytes());
		out.write(("</tr>\n").getBytes());
		out.write(("<tr class=\"popup_row_3\">\n").getBytes());
		out.write(("<td class=\"popup_col_1\"></td>\n").getBytes());
		out.write(("<td class=\"popup_col_2\"></td>\n").getBytes());
		out.write(("<td class=\"popup_col_3 jqResize\"></td>\n").getBytes());
		out.write(("</tr>\n").getBytes());
		out.write(("</table>\n").getBytes());

		addCss(area);
		//addJavascript(component);


	}

	private String getPopupLabel(final HtmlViewRenderable component) {
		SchemaObject schemaObject = ((HtmlViewScreenAreaInstance) component).getForm().getSchemaObject();
		String label = (String) schemaObject.getFeature(ViewAspect.ASPECT_NAME, "label");
		return I18NHelper.getLabel(schemaObject, label);
	}

	protected void addJavascript(final HtmlViewRenderable component) {
		HtmlViewAspectHelper.appendToJsBuffer(component.getHtmlId(), "$('#" + component.getHtmlId()
				+ "').jqDrag('.jqDrag').jqResize('.jqResize');\n");
	}

	public static void removeCss() {
		final StyleBuffer style = HtmlViewAspectHelper.getCssBuffer();
		style.deleteRules(superscreenID);
		style.deleteRules(popup);
		style.deleteRules(popname);
	}

	/**
	 * 
	 * @param area
	 */
	protected void addCss(final HtmlViewScreenArea area) {

		final StyleBuffer style = HtmlViewAspectHelper.getCssBuffer();
		if (!style.containsRule(superscreenID)) {
			style.createRules(superscreenID);
		}
		style.addRule(superscreenID, "background-repeat", "repeat-x");
		style.addRule(superscreenID, CssConstants.POSITION, "fixed");
		style.addRule(superscreenID, CssConstants.TOP, ZERO);
		style.addRule(superscreenID, CssConstants.BOTTOM, ZERO);
		style.addRule(superscreenID, CssConstants.LEFT, ZERO);
		style.addRule(superscreenID, CssConstants.Z_INDEX, Z_INDEX);
		//style.addRule(superscreenID, CssConstants.MARGIN_BOTTOM, "-2000px");
		//style.addRule(superscreenID, CssConstants.PADDING_BOTTOM, "2000px");
		style.addRule(superscreenID, CssConstants.WIDTH, CssConstants._100);
		style.addRule(superscreenID, CssConstants.HEIGHT, CssConstants._100);
		style.addRule(superscreenID, CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath() + "/static/base/image/test01.png)");
/*
		if (!style.containsRule(popup)) {
			style.createRules(popup);
		}
		style.addRule(popup, CssConstants.POSITION, "relative");
		style.addRule(popup, CssConstants.TOP, "80px");
		style.addRule(popup, "overflow", "auto");

		if (!style.containsRule(popname)) {
			style.createRules(popname);
		}
		style.addRule(popname, CssConstants.LINE_HEIGHT, "21px");
		style.addRule(popname, CssConstants.FONT_WEIGHT, "bold");
		style.addRule(popname, CssConstants.COLOR, "#FFFFFF");
		style.addRule(popname, CssConstants.FONT, "14px Verdana, Arial, Helvetica, sans-serif");
		style.addRule(popname, CssConstants.CURSOR, "move");

		if (!style.containsRule(popname + " div")) {
			style.createRules(popname + " div");
		}
		style.addRule(popname + " div", CssConstants.PADDING, "3px;");
		style.addRule(popname + " div", CssConstants.HEIGHT, "17px;");

		if (!style.containsRule(jqResize)) {
			style.createRules(jqResize);
		}
		style.addRule(jqResize, CssConstants.CURSOR, "se-resize");
		final String close = popname + " input." + helper.getHtmlClass(this, "close", null);
		if (!style.containsRule(close)) {
			style.createRules(close);
		}
		style.addRule(close, CssConstants.FLOAT, CssConstants.RIGHT);
		// The same color of line 168
		style.addRule(close, CssConstants.MARGIN, ZERO);
		style.addRule(close, CssConstants.PADDING, "2px");
		style.addRule(close, CssConstants.HEIGHT, HEIGHT);
		style.addRule(close, CssConstants.WIDTH, HEIGHT);
		style.addRule(close, CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath() + "/static/base/image/close.png)");
		style.addRule(close, CssConstants.BORDER, ZERO);

		if (!style.containsRule(close + ":hover")) {
			style.createRules(close + ":hover");
		}
		style.addRule(close + ":hover", CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath() + "/static/base/image/closeOver.png)");

		setHeight(style);

		setWidth(style);

		setBackgroundImage(style);
*/
	}

	private void setHeight(final StyleBuffer style) {
		if (!style.containsRule(row(1))) {
			style.createRules(row(1));
		}
		style.addRule(row(1), CssConstants.HEIGHT, "17px");

		if (!style.containsRule(row(3))) {
			style.createRules(row(3));
		}
		style.addRule(row(3), CssConstants.HEIGHT, HEIGHT);
	}

	private void setWidth(final StyleBuffer style) {
		if (!style.containsRule(col(1))) {
			style.createRules(col(1));
		}
		style.addRule(col(1), CssConstants.WIDTH, "17px");

		if (!style.containsRule(col(3))) {
			style.createRules(col(3));
		}
		style.addRule(col(3), CssConstants.WIDTH, HEIGHT);
	}

	private void setBackgroundImage(final StyleBuffer style) {

		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				if (!style.containsRule(element(i, j))) {
					style.createRules(element(i, j));
				}
			}
		}

		if (!style.containsRule(".popup_left_border")) {
			style.createRules(".popup_left_border");
		}
		
		if (!style.containsRule(".popup_right_border")) {
			style.createRules(".popup_right_border");
		}
		
		
		style
				.addRule(element(1, 1), CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath() + "/static/base/image/BorderTopLeft.png)");
		style.addRule(element(1, 1), CssConstants.BACKGROUND_COLOR, "transparent");
		style.addRule(element(1, 1), CssConstants.PADDING, "0 0");

		style.addRule(".popup_left_border", CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath() + "/static/base/image/BorderLeft.png)");
		style.addRule(".popup_left_border", CssConstants.BACKGROUND_COLOR, "transparent");
		style.addRule(".popup_left_border", CssConstants.PADDING, "0 0");

		style.addRule(element(3, 1), CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath()
				+ "/static/base/image/BorderBottomLeft.png)");
		style.addRule(element(3, 1), CssConstants.BACKGROUND_COLOR, "transparent");
		style.addRule(element(3, 1), CssConstants.PADDING, "0 0");

		style.addRule(element(1, 2), CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath() + "/static/base/image/BorderTop.png)");
		style.addRule(element(1, 2), CssConstants.BACKGROUND_COLOR, "transparent");
		style.addRule(element(1, 2), CssConstants.PADDING, "0 0");

		style.addRule(element(2, 2), CssConstants.BACKGROUND_COLOR, "#FFFFFF");
		style.addRule(element(2, 2), "vertical-align", CssConstants.TOP);

		style.addRule(element(3, 2), CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath() + "/static/base/image/BorderBottom.png)");
		style.addRule(element(3, 2), CssConstants.BACKGROUND_COLOR, "transparent");
		style.addRule(element(3, 2), CssConstants.PADDING, "0 0");

		style.addRule(element(1, 3), CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath()
				+ "/static/base/image/BorderTopRight.png)");
		style.addRule(element(1, 3), CssConstants.BACKGROUND_COLOR, "transparent");
		style.addRule(element(1, 3), CssConstants.PADDING, "0 0");

		style.addRule(".popup_right_border", CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath() + "/static/base/image/BorderRight.png)");
		style.addRule(".popup_right_border", CssConstants.BACKGROUND_COLOR, "transparent");
		style.addRule(".popup_right_border", CssConstants.PADDING, "0 0");

		style.addRule(element(3, 3), CssConstants.BACKGROUND_IMAGE, "url(" + getContextPath()
				+ "/static/base/image/BorderBottomRight.png)");
		style.addRule(element(3, 3), CssConstants.BACKGROUND_COLOR, "transparent");
		style.addRule(element(3, 3), CssConstants.PADDING, "0 0");
	}

	@Override
	public String toString() {
		return Screen.POPUP;
	}

	private String getContextPath() {
		HttpServletRequest request = HttpAbstractSessionAspect.getServletRequest();
		if (request == null)
			return "";
		return request.getContextPath();
	}

	public String getType() {
		return Transformer.PRIMITIVE;
	}
}
