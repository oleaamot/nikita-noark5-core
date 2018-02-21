// Generated from /home/tsodring/git/nikita-noark5-core/core-webapp/src/main/resources/odata/OData.g4 by ANTLR 4.7
package no.arkivlab.hioa.nikita.webapp.test.odata;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ODataParser extends Parser {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, T__11 = 12, WHITESPACE = 13, EQ = 14, GT = 15, LT = 16, GE = 17,
            LE = 18, AND = 19, OR = 20, TOP = 21, SKIP_ = 22, COUNT = 23, ORDERBY = 24, WS = 25, DIGITS = 26,
            HEX = 27, STRING = 28, COLON = 29, SEPERATOR = 30;
    public static final int
            RULE_odataURL = 0, RULE_scheme = 1, RULE_host = 2, RULE_slash = 3, RULE_contextPath = 4,
            RULE_api = 5, RULE_functionality = 6, RULE_resource = 7, RULE_port = 8,
            RULE_odataCommand = 9, RULE_filter = 10, RULE_search = 11, RULE_searchCommand = 12,
            RULE_filterCommand = 13, RULE_command = 14, RULE_contains = 15, RULE_startsWith = 16,
            RULE_attribute = 17, RULE_value = 18, RULE_comparator = 19, RULE_operator = 20,
            RULE_leftCurlyBracket = 21, RULE_rightCurlyBracket = 22, RULE_and = 23,
            RULE_or = 24, RULE_eq = 25, RULE_gt = 26, RULE_lt = 27, RULE_ge = 28,
            RULE_le = 29, RULE_string = 30;
    public static final String[] ruleNames = {
            "odataURL", "scheme", "host", "slash", "contextPath", "api", "functionality",
            "resource", "port", "odataCommand", "filter", "search", "searchCommand",
            "filterCommand", "command", "contains", "startsWith", "attribute", "value",
            "comparator", "operator", "leftCurlyBracket", "rightCurlyBracket", "and",
            "or", "eq", "gt", "lt", "ge", "le", "string"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3 \u00ae\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
                    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \3\2" +
                    "\3\2\3\2\3\2\3\2\5\2F\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4" +
                    "\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13" +
                    "\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17" +
                    "\3\17\5\17t\n\17\3\17\3\17\3\17\5\17y\n\17\3\20\3\20\5\20}\n\20\3\20\3" +
                    "\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3" +
                    "\24\3\25\3\25\3\25\3\25\3\25\5\25\u0094\n\25\3\26\3\26\5\26\u0098\n\26" +
                    "\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35" +
                    "\3\36\3\36\3\37\3\37\3 \3 \3 \2\2!\2\4\6\b\n\f\16\20\22\24\26\30\32\34" +
                    "\36 \"$&(*,.\60\62\64\668:<>\2\3\3\2\4\5\2\u0097\2@\3\2\2\2\4N\3\2\2\2" +
                    "\6P\3\2\2\2\bR\3\2\2\2\nT\3\2\2\2\fW\3\2\2\2\16Z\3\2\2\2\20]\3\2\2\2\22" +
                    "_\3\2\2\2\24a\3\2\2\2\26d\3\2\2\2\30g\3\2\2\2\32j\3\2\2\2\34s\3\2\2\2" +
                    "\36|\3\2\2\2 \u0086\3\2\2\2\"\u0088\3\2\2\2$\u008a\3\2\2\2&\u008c\3\2" +
                    "\2\2(\u0093\3\2\2\2*\u0097\3\2\2\2,\u0099\3\2\2\2.\u009b\3\2\2\2\60\u009d" +
                    "\3\2\2\2\62\u009f\3\2\2\2\64\u00a1\3\2\2\2\66\u00a3\3\2\2\28\u00a5\3\2" +
                    "\2\2:\u00a7\3\2\2\2<\u00a9\3\2\2\2>\u00ab\3\2\2\2@A\5\4\3\2AB\7 \2\2B" +
                    "E\5\6\4\2CD\7\37\2\2DF\5\22\n\2EC\3\2\2\2EF\3\2\2\2FG\3\2\2\2GH\5\n\6" +
                    "\2HI\5\f\7\2IJ\5\16\b\2JK\7\3\2\2KL\5\20\t\2LM\5\24\13\2M\3\3\2\2\2NO" +
                    "\t\2\2\2O\5\3\2\2\2PQ\5> \2Q\7\3\2\2\2RS\7\3\2\2S\t\3\2\2\2TU\7\3\2\2" +
                    "UV\5> \2V\13\3\2\2\2WX\7\3\2\2XY\5> \2Y\r\3\2\2\2Z[\7\3\2\2[\\\5> \2\\" +
                    "\17\3\2\2\2]^\5> \2^\21\3\2\2\2_`\7\34\2\2`\23\3\2\2\2ab\7\6\2\2bc\5\26" +
                    "\f\2c\25\3\2\2\2de\7\7\2\2ef\5\34\17\2f\27\3\2\2\2gh\7\b\2\2hi\5\32\16" +
                    "\2i\31\3\2\2\2jk\5> \2k\33\3\2\2\2lt\5\36\20\2mn\5$\23\2no\5(\25\2op\7" +
                    "\t\2\2pq\5&\24\2qr\7\t\2\2rt\3\2\2\2sl\3\2\2\2sm\3\2\2\2tx\3\2\2\2uv\5" +
                    "*\26\2vw\5\34\17\2wy\3\2\2\2xu\3\2\2\2xy\3\2\2\2y\35\3\2\2\2z}\5 \21\2" +
                    "{}\5\"\22\2|z\3\2\2\2|{\3\2\2\2}~\3\2\2\2~\177\5,\27\2\177\u0080\5$\23" +
                    "\2\u0080\u0081\7\n\2\2\u0081\u0082\7\t\2\2\u0082\u0083\5&\24\2\u0083\u0084" +
                    "\7\t\2\2\u0084\u0085\5.\30\2\u0085\37\3\2\2\2\u0086\u0087\7\13\2\2\u0087" +
                    "!\3\2\2\2\u0088\u0089\7\f\2\2\u0089#\3\2\2\2\u008a\u008b\5> \2\u008b%" +
                    "\3\2\2\2\u008c\u008d\5> \2\u008d\'\3\2\2\2\u008e\u0094\5\64\33\2\u008f" +
                    "\u0094\5\66\34\2\u0090\u0094\58\35\2\u0091\u0094\5:\36\2\u0092\u0094\5" +
                    "<\37\2\u0093\u008e\3\2\2\2\u0093\u008f\3\2\2\2\u0093\u0090\3\2\2\2\u0093" +
                    "\u0091\3\2\2\2\u0093\u0092\3\2\2\2\u0094)\3\2\2\2\u0095\u0098\5\60\31" +
                    "\2\u0096\u0098\5\62\32\2\u0097\u0095\3\2\2\2\u0097\u0096\3\2\2\2\u0098" +
                    "+\3\2\2\2\u0099\u009a\7\r\2\2\u009a-\3\2\2\2\u009b\u009c\7\16\2\2\u009c" +
                    "/\3\2\2\2\u009d\u009e\7\25\2\2\u009e\61\3\2\2\2\u009f\u00a0\7\26\2\2\u00a0" +
                    "\63\3\2\2\2\u00a1\u00a2\7\20\2\2\u00a2\65\3\2\2\2\u00a3\u00a4\7\21\2\2" +
                    "\u00a4\67\3\2\2\2\u00a5\u00a6\7\22\2\2\u00a69\3\2\2\2\u00a7\u00a8\7\23" +
                    "\2\2\u00a8;\3\2\2\2\u00a9\u00aa\7\24\2\2\u00aa=\3\2\2\2\u00ab\u00ac\7" +
                    "\36\2\2\u00ac?\3\2\2\2\bEsx|\u0093\u0097";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {
            null, "'/'", "'http'", "'https'", "'?'", "'$filter='", "'$search='", "'''",
            "','", "'contains'", "'startsWith'", "'('", "')'", null, "'eq'", "'gt'",
            "'lt'", "'ge'", "'le'", "'and'", "'or'", "'top'", "'skip'", "'count'",
            "'orderby'", null, null, null, null, "':'", "'://'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, "WHITESPACE", "EQ", "GT", "LT", "GE", "LE", "AND", "OR", "TOP",
            "SKIP_", "COUNT", "ORDERBY", "WS", "DIGITS", "HEX", "STRING", "COLON",
            "SEPERATOR"
    };
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    static {
        RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION);
    }

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public ODataParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "OData.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public final OdataURLContext odataURL() throws RecognitionException {
        OdataURLContext _localctx = new OdataURLContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_odataURL);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(62);
                scheme();
                setState(63);
                match(SEPERATOR);
                setState(64);
                host();
                setState(67);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == COLON) {
                    {
                        setState(65);
                        match(COLON);
                        setState(66);
                        port();
                    }
                }

                setState(69);
                contextPath();
                setState(70);
                api();
                setState(71);
                functionality();
                setState(72);
                match(T__0);
                setState(73);
                resource();
                setState(74);
                odataCommand();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final SchemeContext scheme() throws RecognitionException {
        SchemeContext _localctx = new SchemeContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_scheme);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(76);
                _la = _input.LA(1);
                if (!(_la == T__1 || _la == T__2)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final HostContext host() throws RecognitionException {
        HostContext _localctx = new HostContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_host);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(78);
                string();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final SlashContext slash() throws RecognitionException {
        SlashContext _localctx = new SlashContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_slash);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(80);
                match(T__0);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ContextPathContext contextPath() throws RecognitionException {
        ContextPathContext _localctx = new ContextPathContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_contextPath);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(82);
                match(T__0);
                setState(83);
                string();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ApiContext api() throws RecognitionException {
        ApiContext _localctx = new ApiContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_api);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(85);
                match(T__0);
                setState(86);
                string();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final FunctionalityContext functionality() throws RecognitionException {
        FunctionalityContext _localctx = new FunctionalityContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_functionality);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(88);
                match(T__0);
                setState(89);
                string();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ResourceContext resource() throws RecognitionException {
        ResourceContext _localctx = new ResourceContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_resource);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(91);
                string();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final PortContext port() throws RecognitionException {
        PortContext _localctx = new PortContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_port);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(93);
                match(DIGITS);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final OdataCommandContext odataCommand() throws RecognitionException {
        OdataCommandContext _localctx = new OdataCommandContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_odataCommand);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(95);
                match(T__3);
                setState(96);
                filter();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final FilterContext filter() throws RecognitionException {
        FilterContext _localctx = new FilterContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_filter);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(98);
                match(T__4);
                setState(99);
                filterCommand();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final SearchContext search() throws RecognitionException {
        SearchContext _localctx = new SearchContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_search);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(101);
                match(T__5);
                setState(102);
                searchCommand();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final SearchCommandContext searchCommand() throws RecognitionException {
        SearchCommandContext _localctx = new SearchCommandContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_searchCommand);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(104);
                string();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final FilterCommandContext filterCommand() throws RecognitionException {
        FilterCommandContext _localctx = new FilterCommandContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_filterCommand);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(113);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__8:
                    case T__9: {
                        setState(106);
                        command();
                    }
                    break;
                    case STRING: {
                        {
                            setState(107);
                            attribute();
                            setState(108);
                            comparator();
                            setState(109);
                            match(T__6);
                            setState(110);
                            value();
                            setState(111);
                            match(T__6);
                        }
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(118);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == AND || _la == OR) {
                    {
                        setState(115);
                        operator();
                        setState(116);
                        filterCommand();
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final CommandContext command() throws RecognitionException {
        CommandContext _localctx = new CommandContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_command);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(122);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__8: {
                        setState(120);
                        contains();
                    }
                    break;
                    case T__9: {
                        setState(121);
                        startsWith();
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(124);
                leftCurlyBracket();
                setState(125);
                attribute();
                setState(126);
                match(T__7);
                setState(127);
                match(T__6);
                setState(128);
                value();
                setState(129);
                match(T__6);
                setState(130);
                rightCurlyBracket();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ContainsContext contains() throws RecognitionException {
        ContainsContext _localctx = new ContainsContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_contains);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(132);
                match(T__8);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final StartsWithContext startsWith() throws RecognitionException {
        StartsWithContext _localctx = new StartsWithContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_startsWith);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(134);
                match(T__9);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final AttributeContext attribute() throws RecognitionException {
        AttributeContext _localctx = new AttributeContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_attribute);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(136);
                string();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ValueContext value() throws RecognitionException {
        ValueContext _localctx = new ValueContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_value);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(138);
                string();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ComparatorContext comparator() throws RecognitionException {
        ComparatorContext _localctx = new ComparatorContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_comparator);
        try {
            setState(145);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case EQ:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(140);
                    eq();
                }
                break;
                case GT:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(141);
                    gt();
                }
                break;
                case LT:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(142);
                    lt();
                }
                break;
                case GE:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(143);
                    ge();
                }
                break;
                case LE:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(144);
                    le();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final OperatorContext operator() throws RecognitionException {
        OperatorContext _localctx = new OperatorContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_operator);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(149);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case AND: {
                        setState(147);
                        and();
                    }
                    break;
                    case OR: {
                        setState(148);
                        or();
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final LeftCurlyBracketContext leftCurlyBracket() throws RecognitionException {
        LeftCurlyBracketContext _localctx = new LeftCurlyBracketContext(_ctx, getState());
        enterRule(_localctx, 42, RULE_leftCurlyBracket);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(151);
                match(T__10);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final RightCurlyBracketContext rightCurlyBracket() throws RecognitionException {
        RightCurlyBracketContext _localctx = new RightCurlyBracketContext(_ctx, getState());
        enterRule(_localctx, 44, RULE_rightCurlyBracket);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(153);
                match(T__11);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final AndContext and() throws RecognitionException {
        AndContext _localctx = new AndContext(_ctx, getState());
        enterRule(_localctx, 46, RULE_and);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(155);
                match(AND);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final OrContext or() throws RecognitionException {
        OrContext _localctx = new OrContext(_ctx, getState());
        enterRule(_localctx, 48, RULE_or);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(157);
                match(OR);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final EqContext eq() throws RecognitionException {
        EqContext _localctx = new EqContext(_ctx, getState());
        enterRule(_localctx, 50, RULE_eq);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(159);
                match(EQ);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final GtContext gt() throws RecognitionException {
        GtContext _localctx = new GtContext(_ctx, getState());
        enterRule(_localctx, 52, RULE_gt);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(161);
                match(GT);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final LtContext lt() throws RecognitionException {
        LtContext _localctx = new LtContext(_ctx, getState());
        enterRule(_localctx, 54, RULE_lt);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(163);
                match(LT);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final GeContext ge() throws RecognitionException {
        GeContext _localctx = new GeContext(_ctx, getState());
        enterRule(_localctx, 56, RULE_ge);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(165);
                match(GE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final LeContext le() throws RecognitionException {
        LeContext _localctx = new LeContext(_ctx, getState());
        enterRule(_localctx, 58, RULE_le);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(167);
                match(LE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final StringContext string() throws RecognitionException {
        StringContext _localctx = new StringContext(_ctx, getState());
        enterRule(_localctx, 60, RULE_string);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(169);
                match(STRING);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OdataURLContext extends ParserRuleContext {
        public OdataURLContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public SchemeContext scheme() {
            return getRuleContext(SchemeContext.class, 0);
        }

        public TerminalNode SEPERATOR() {
            return getToken(ODataParser.SEPERATOR, 0);
        }

        public HostContext host() {
            return getRuleContext(HostContext.class, 0);
        }

        public ContextPathContext contextPath() {
            return getRuleContext(ContextPathContext.class, 0);
        }

        public ApiContext api() {
            return getRuleContext(ApiContext.class, 0);
        }

        public FunctionalityContext functionality() {
            return getRuleContext(FunctionalityContext.class, 0);
        }

        public ResourceContext resource() {
            return getRuleContext(ResourceContext.class, 0);
        }

        public OdataCommandContext odataCommand() {
            return getRuleContext(OdataCommandContext.class, 0);
        }

        public TerminalNode COLON() {
            return getToken(ODataParser.COLON, 0);
        }

        public PortContext port() {
            return getRuleContext(PortContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_odataURL;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterOdataURL(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitOdataURL(this);
        }
    }

    public static class SchemeContext extends ParserRuleContext {
        public SchemeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_scheme;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterScheme(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitScheme(this);
        }
    }

    public static class HostContext extends ParserRuleContext {
        public HostContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_host;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterHost(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitHost(this);
        }
    }

    public static class SlashContext extends ParserRuleContext {
        public SlashContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_slash;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterSlash(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitSlash(this);
        }
    }

    public static class ContextPathContext extends ParserRuleContext {
        public ContextPathContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_contextPath;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterContextPath(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitContextPath(this);
        }
    }

    public static class ApiContext extends ParserRuleContext {
        public ApiContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_api;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterApi(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitApi(this);
        }
    }

    public static class FunctionalityContext extends ParserRuleContext {
        public FunctionalityContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_functionality;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterFunctionality(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitFunctionality(this);
        }
    }

    public static class ResourceContext extends ParserRuleContext {
        public ResourceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_resource;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterResource(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitResource(this);
        }
    }

    public static class PortContext extends ParserRuleContext {
        public PortContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode DIGITS() {
            return getToken(ODataParser.DIGITS, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_port;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterPort(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitPort(this);
        }
    }

    public static class OdataCommandContext extends ParserRuleContext {
        public OdataCommandContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public FilterContext filter() {
            return getRuleContext(FilterContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_odataCommand;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterOdataCommand(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitOdataCommand(this);
        }
    }

    public static class FilterContext extends ParserRuleContext {
        public FilterContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public FilterCommandContext filterCommand() {
            return getRuleContext(FilterCommandContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_filter;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterFilter(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitFilter(this);
        }
    }

    public static class SearchContext extends ParserRuleContext {
        public SearchContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public SearchCommandContext searchCommand() {
            return getRuleContext(SearchCommandContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_search;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterSearch(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitSearch(this);
        }
    }

    public static class SearchCommandContext extends ParserRuleContext {
        public SearchCommandContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_searchCommand;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterSearchCommand(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitSearchCommand(this);
        }
    }

    public static class FilterCommandContext extends ParserRuleContext {
        public FilterCommandContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public CommandContext command() {
            return getRuleContext(CommandContext.class, 0);
        }

        public OperatorContext operator() {
            return getRuleContext(OperatorContext.class, 0);
        }

        public FilterCommandContext filterCommand() {
            return getRuleContext(FilterCommandContext.class, 0);
        }

        public AttributeContext attribute() {
            return getRuleContext(AttributeContext.class, 0);
        }

        public ComparatorContext comparator() {
            return getRuleContext(ComparatorContext.class, 0);
        }

        public ValueContext value() {
            return getRuleContext(ValueContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_filterCommand;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterFilterCommand(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitFilterCommand(this);
        }
    }

    public static class CommandContext extends ParserRuleContext {
        public CommandContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public LeftCurlyBracketContext leftCurlyBracket() {
            return getRuleContext(LeftCurlyBracketContext.class, 0);
        }

        public AttributeContext attribute() {
            return getRuleContext(AttributeContext.class, 0);
        }

        public ValueContext value() {
            return getRuleContext(ValueContext.class, 0);
        }

        public RightCurlyBracketContext rightCurlyBracket() {
            return getRuleContext(RightCurlyBracketContext.class, 0);
        }

        public ContainsContext contains() {
            return getRuleContext(ContainsContext.class, 0);
        }

        public StartsWithContext startsWith() {
            return getRuleContext(StartsWithContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_command;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterCommand(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitCommand(this);
        }
    }

    public static class ContainsContext extends ParserRuleContext {
        public ContainsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_contains;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterContains(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitContains(this);
        }
    }

    public static class StartsWithContext extends ParserRuleContext {
        public StartsWithContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_startsWith;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterStartsWith(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitStartsWith(this);
        }
    }

    public static class AttributeContext extends ParserRuleContext {
        public AttributeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_attribute;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterAttribute(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitAttribute(this);
        }
    }

    public static class ValueContext extends ParserRuleContext {
        public ValueContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StringContext string() {
            return getRuleContext(StringContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_value;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitValue(this);
        }
    }

    public static class ComparatorContext extends ParserRuleContext {
        public ComparatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public EqContext eq() {
            return getRuleContext(EqContext.class, 0);
        }

        public GtContext gt() {
            return getRuleContext(GtContext.class, 0);
        }

        public LtContext lt() {
            return getRuleContext(LtContext.class, 0);
        }

        public GeContext ge() {
            return getRuleContext(GeContext.class, 0);
        }

        public LeContext le() {
            return getRuleContext(LeContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_comparator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterComparator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitComparator(this);
        }
    }

    public static class OperatorContext extends ParserRuleContext {
        public OperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public AndContext and() {
            return getRuleContext(AndContext.class, 0);
        }

        public OrContext or() {
            return getRuleContext(OrContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_operator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitOperator(this);
        }
    }

    public static class LeftCurlyBracketContext extends ParserRuleContext {
        public LeftCurlyBracketContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_leftCurlyBracket;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterLeftCurlyBracket(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitLeftCurlyBracket(this);
        }
    }

    public static class RightCurlyBracketContext extends ParserRuleContext {
        public RightCurlyBracketContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_rightCurlyBracket;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterRightCurlyBracket(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitRightCurlyBracket(this);
        }
    }

    public static class AndContext extends ParserRuleContext {
        public AndContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode AND() {
            return getToken(ODataParser.AND, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_and;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterAnd(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitAnd(this);
        }
    }

    public static class OrContext extends ParserRuleContext {
        public OrContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode OR() {
            return getToken(ODataParser.OR, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_or;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterOr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitOr(this);
        }
    }

    public static class EqContext extends ParserRuleContext {
        public EqContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode EQ() {
            return getToken(ODataParser.EQ, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_eq;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterEq(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitEq(this);
        }
    }

    public static class GtContext extends ParserRuleContext {
        public GtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode GT() {
            return getToken(ODataParser.GT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_gt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterGt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitGt(this);
        }
    }

    public static class LtContext extends ParserRuleContext {
        public LtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode LT() {
            return getToken(ODataParser.LT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_lt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterLt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitLt(this);
        }
    }

    public static class GeContext extends ParserRuleContext {
        public GeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode GE() {
            return getToken(ODataParser.GE, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_ge;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterGe(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitGe(this);
        }
    }

    public static class LeContext extends ParserRuleContext {
        public LeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode LE() {
            return getToken(ODataParser.LE, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_le;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterLe(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitLe(this);
        }
    }

    public static class StringContext extends ParserRuleContext {
        public StringContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode STRING() {
            return getToken(ODataParser.STRING, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_string;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterString(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitString(this);
        }
    }
}
