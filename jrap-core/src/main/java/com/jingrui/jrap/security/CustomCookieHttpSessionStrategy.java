package com.jingrui.jrap.security;

import com.jingrui.jrap.core.components.SysConfigManager;
import org.springframework.session.Session;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionManager;
import org.springframework.session.web.http.MultiHttpSessionStrategy;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author jialong.zuo@jingrui.com
 * @date 2017/3/14.
 */
public class CustomCookieHttpSessionStrategy implements MultiHttpSessionStrategy, HttpSessionManager {

    private static final String SESSION_IDS_WRITTEN_ATTR = CustomCookieHttpSessionStrategy.class.getName().concat(".SESSIONS_WRITTEN_ATTR");
    private static final String DEFAULT_ALIAS = "0";
    private static final Pattern ALIAS_PATTERN = Pattern.compile("^[\\w-]{1,50}$");
    private String sessionParam = "_s";
    private CookieSerializer cookieSerializer = new DefaultCookieSerializer();
    private String deserializationDelimiter = " ";
    private String serializationDelimiter = " ";


    public CustomCookieHttpSessionStrategy() {
    }

    @Override
    public String getRequestedSessionId(HttpServletRequest request) {
        Map sessionIds = this.getSessionIds(request);
        String sessionAlias = this.getCurrentSessionAlias(request);
        return (String) sessionIds.get(sessionAlias);
    }

    @Override
    public String getCurrentSessionAlias(HttpServletRequest request) {
        if (this.sessionParam == null) {
            return DEFAULT_ALIAS;
        } else {
            String u = request.getParameter(this.sessionParam);
            return u == null ? DEFAULT_ALIAS : (!ALIAS_PATTERN.matcher(u).matches() ? DEFAULT_ALIAS : u);
        }
    }

    @Override
    public String getNewSessionAlias(HttpServletRequest request) {
        Set sessionAliases = this.getSessionIds(request).keySet();
        if (sessionAliases.isEmpty()) {
            return DEFAULT_ALIAS;
        } else {
            long lastAlias = Long.decode(DEFAULT_ALIAS).longValue();
            Iterator var5 = sessionAliases.iterator();

            while (var5.hasNext()) {
                String alias = (String) var5.next();
                long selectedAlias = this.safeParse(alias);
                if (selectedAlias > lastAlias) {
                    lastAlias = selectedAlias;
                }
            }

            return Long.toHexString(lastAlias + 1L);
        }
    }

    private long safeParse(String hex) {
        try {
            return Long.decode("0x" + hex).longValue();
        } catch (NumberFormatException var3) {
            return 0L;
        }
    }

    @Override
    public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
        Set sessionIdsWritten = this.getSessionIdsWritten(request);
        if (!sessionIdsWritten.contains(session.getId())) {
            sessionIdsWritten.add(session.getId());
            Map sessionIds = this.getSessionIds(request);
            String sessionAlias = this.getCurrentSessionAlias(request);
            sessionIds.put(sessionAlias, session.getId());
            String cookieValue = this.createSessionCookieValue(sessionIds);
            ((DefaultCookieSerializer) this.cookieSerializer).setUseHttpOnlyCookie(true);
            if (SysConfigManager.useHttps) {
                ((DefaultCookieSerializer) this.cookieSerializer).setUseSecureCookie(true);
            }
            this.cookieSerializer.writeCookieValue(new CookieSerializer.CookieValue(request, response, cookieValue));
        }
    }

    private Set<String> getSessionIdsWritten(HttpServletRequest request) {
        Object sessionsWritten = request.getAttribute(SESSION_IDS_WRITTEN_ATTR);
        if (sessionsWritten == null) {
            sessionsWritten = new HashSet();
            request.setAttribute(SESSION_IDS_WRITTEN_ATTR, sessionsWritten);
        }

        return (Set) sessionsWritten;
    }

    private String createSessionCookieValue(Map<String, String> sessionIds) {
        if (sessionIds.isEmpty()) {
            return "";
        } else if (sessionIds.size() == 1 && sessionIds.keySet().contains("0")) {
            return sessionIds.values().iterator().next();
        } else {
            StringBuffer buffer = new StringBuffer();
            Iterator var3 = sessionIds.entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry entry = (Map.Entry) var3.next();
                String alias = (String) entry.getKey();
                String id = (String) entry.getValue();
                buffer.append(alias);
                buffer.append(this.serializationDelimiter);
                buffer.append(id);
                buffer.append(this.serializationDelimiter);
            }

            buffer.deleteCharAt(buffer.length() - 1);
            return buffer.toString();
        }
    }

    @Override
    public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
        Map sessionIds = this.getSessionIds(request);
        String requestedAlias = this.getCurrentSessionAlias(request);
        sessionIds.remove(requestedAlias);
        String cookieValue = this.createSessionCookieValue(sessionIds);
        this.cookieSerializer.writeCookieValue(new CookieSerializer.CookieValue(request, response, cookieValue));
    }

    public void setSessionAliasParamName(String sessionAliasParamName) {
        this.sessionParam = sessionAliasParamName;
    }

    public void setCookieSerializer(CookieSerializer cookieSerializer) {
        Assert.notNull(cookieSerializer, "cookieSerializer cannot be null");
        this.cookieSerializer = cookieSerializer;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setCookieName(String cookieName) {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(cookieName);
        this.cookieSerializer = serializer;
    }

    public void setDeserializationDelimiter(String delimiter) {
        this.deserializationDelimiter = delimiter;
    }

    public void setSerializationDelimiter(String delimiter) {
        this.serializationDelimiter = delimiter;
    }

    @Override
    public Map<String, String> getSessionIds(HttpServletRequest request) {
        List cookieValues = this.cookieSerializer.readCookieValues(request);
        if (cookieValues.isEmpty()) {
            String sessionId = request.getHeader("Authorization");
            if (null != sessionId) {
                cookieValues.add(sessionId);
            }
        }
        String sessionCookieValue = cookieValues.isEmpty() ? "" : (String) cookieValues.iterator().next();
        LinkedHashMap result = new LinkedHashMap();
        StringTokenizer tokens = new StringTokenizer(sessionCookieValue, this.deserializationDelimiter);
        if (tokens.countTokens() == 1) {
            result.put(DEFAULT_ALIAS, tokens.nextToken());
            return result;
        } else {
            while (tokens.hasMoreTokens()) {
                String alias = tokens.nextToken();
                if (!tokens.hasMoreTokens()) {
                    break;
                }

                String id = tokens.nextToken();
                result.put(alias, id);
            }

            return result;
        }
    }

    @Override
    public HttpServletRequest wrapRequest(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(HttpSessionManager.class.getName(), this);
        return request;
    }


    @Override
    public HttpServletResponse wrapResponse(HttpServletRequest request, HttpServletResponse response) {
        return new CustomCookieHttpSessionStrategy.MultiSessionHttpServletResponse(response, request);
    }

    @Override
    public String encodeURL(String url, String sessionAlias) {
        String encodedSessionAlias = this.urlEncode(sessionAlias);
        int queryStart = url.indexOf("?");
        boolean isDefaultAlias = DEFAULT_ALIAS.equals(encodedSessionAlias);
        if (queryStart < 0) {
            return isDefaultAlias ? url : url + "?" + this.sessionParam + "=" + encodedSessionAlias;
        } else {
            String path = url.substring(0, queryStart);
            String query = url.substring(queryStart + 1, url.length());
            String replacement = isDefaultAlias ? "" : "$1" + encodedSessionAlias;
            query = query.replaceFirst("((^|&)" + this.sessionParam + "=)([^&]+)?", replacement);
            if (!isDefaultAlias && url.endsWith(query)) {
                if (!query.endsWith("&") && query.length() != 0) {
                    query = query + "&";
                }

                query = query + this.sessionParam + "=" + encodedSessionAlias;
            }

            return path + "?" + query;
        }
    }

    private String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            throw new RuntimeException(var3);
        }
    }

    class MultiSessionHttpServletResponse extends HttpServletResponseWrapper {
        private final HttpServletRequest request;

        MultiSessionHttpServletResponse(HttpServletResponse response, HttpServletRequest request) {
            super(response);
            this.request = request;
        }

        @Override
        public String encodeRedirectURL(String url) {
            url = super.encodeRedirectURL(url);
            return CustomCookieHttpSessionStrategy.this.encodeURL(url, CustomCookieHttpSessionStrategy.this.getCurrentSessionAlias(this.request));
        }

        @Override
        public String encodeURL(String url) {
            url = super.encodeURL(url);
            String alias = CustomCookieHttpSessionStrategy.this.getCurrentSessionAlias(this.request);
            return CustomCookieHttpSessionStrategy.this.encodeURL(url, alias);
        }
    }
}
