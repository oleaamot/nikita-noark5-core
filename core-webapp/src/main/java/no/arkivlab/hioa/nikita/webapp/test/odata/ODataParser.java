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
            RULE_attribute = 17, RULE_value = 18, RULE_top = 19, RULE_skip = 20, RULE_comparator = 21,
            RULE_operator = 22, RULE_leftCurlyBracket = 23, RULE_rightCurlyBracket = 24,
            RULE_and = 25, RULE_or = 26, RULE_eq = 27, RULE_gt = 28, RULE_lt = 29,
            RULE_ge = 30, RULE_le = 31, RULE_string = 32;
    public static final String[] ruleNames = {
            "odataURL", "scheme", "host", "slash", "contextPath", "api", "functionality",
            "resource", "port", "odataCommand", "filter", "search", "searchCommand",
            "filterCommand", "command", "contains", "startsWith", "attribute", "value",
            "top", "skip", "comparator", "operator", "leftCurlyBracket", "rightCurlyBracket",
            "and", "or", "eq", "gt", "lt", "ge", "le", "string"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3 \u00bc\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
                    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!" +
                    "\t!\4\"\t\"\3\2\3\2\3\2\3\2\3\2\5\2J\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3" +
                    "\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n" +
                    "\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3" +
                    "\17\3\17\3\17\3\17\5\17x\n\17\3\17\3\17\3\17\5\17}\n\17\3\20\3\20\5\20" +
                    "\u0081\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22" +
                    "\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26" +
                    "\3\27\3\27\3\27\3\27\3\27\5\27\u00a2\n\27\3\30\3\30\5\30\u00a6\n\30\3" +
                    "\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3" +
                    " \3 \3!\3!\3\"\3\"\3\"\2\2#\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"" +
                    "$&(*,.\60\62\64\668:<>@B\2\3\3\2\4\5\2\u00a3\2D\3\2\2\2\4R\3\2\2\2\6T" +
                    "\3\2\2\2\bV\3\2\2\2\nX\3\2\2\2\f[\3\2\2\2\16^\3\2\2\2\20a\3\2\2\2\22c" +
                    "\3\2\2\2\24e\3\2\2\2\26h\3\2\2\2\30k\3\2\2\2\32n\3\2\2\2\34w\3\2\2\2\36" +
                    "\u0080\3\2\2\2 \u0082\3\2\2\2\"\u008b\3\2\2\2$\u0094\3\2\2\2&\u0096\3" +
                    "\2\2\2(\u0098\3\2\2\2*\u009a\3\2\2\2,\u00a1\3\2\2\2.\u00a5\3\2\2\2\60" +
                    "\u00a7\3\2\2\2\62\u00a9\3\2\2\2\64\u00ab\3\2\2\2\66\u00ad\3\2\2\28\u00af" +
                    "\3\2\2\2:\u00b1\3\2\2\2<\u00b3\3\2\2\2>\u00b5\3\2\2\2@\u00b7\3\2\2\2B" +
                    "\u00b9\3\2\2\2DE\5\4\3\2EF\7 \2\2FI\5\6\4\2GH\7\37\2\2HJ\5\22\n\2IG\3" +
                    "\2\2\2IJ\3\2\2\2JK\3\2\2\2KL\5\n\6\2LM\5\f\7\2MN\5\16\b\2NO\7\3\2\2OP" +
                    "\5\20\t\2PQ\5\24\13\2Q\3\3\2\2\2RS\t\2\2\2S\5\3\2\2\2TU\5B\"\2U\7\3\2" +
                    "\2\2VW\7\3\2\2W\t\3\2\2\2XY\7\3\2\2YZ\5B\"\2Z\13\3\2\2\2[\\\7\3\2\2\\" +
                    "]\5B\"\2]\r\3\2\2\2^_\7\3\2\2_`\5B\"\2`\17\3\2\2\2ab\5B\"\2b\21\3\2\2" +
                    "\2cd\7\34\2\2d\23\3\2\2\2ef\7\6\2\2fg\5\26\f\2g\25\3\2\2\2hi\7\7\2\2i" +
                    "j\5\34\17\2j\27\3\2\2\2kl\7\b\2\2lm\5\32\16\2m\31\3\2\2\2no\5B\"\2o\33" +
                    "\3\2\2\2px\5\36\20\2qr\5$\23\2rs\5,\27\2st\7\t\2\2tu\5&\24\2uv\7\t\2\2" +
                    "vx\3\2\2\2wp\3\2\2\2wq\3\2\2\2x|\3\2\2\2yz\5.\30\2z{\5\34\17\2{}\3\2\2" +
                    "\2|y\3\2\2\2|}\3\2\2\2}\35\3\2\2\2~\u0081\5 \21\2\177\u0081\5\"\22\2\u0080" +
                    "~\3\2\2\2\u0080\177\3\2\2\2\u0081\37\3\2\2\2\u0082\u0083\7\n\2\2\u0083" +
                    "\u0084\5\60\31\2\u0084\u0085\5$\23\2\u0085\u0086\7\13\2\2\u0086\u0087" +
                    "\7\t\2\2\u0087\u0088\5&\24\2\u0088\u0089\7\t\2\2\u0089\u008a\5\62\32\2" +
                    "\u008a!\3\2\2\2\u008b\u008c\7\f\2\2\u008c\u008d\5\60\31\2\u008d\u008e" +
                    "\5$\23\2\u008e\u008f\7\13\2\2\u008f\u0090\7\t\2\2\u0090\u0091\5&\24\2" +
                    "\u0091\u0092\7\t\2\2\u0092\u0093\5\62\32\2\u0093#\3\2\2\2\u0094\u0095" +
                    "\5B\"\2\u0095%\3\2\2\2\u0096\u0097\5B\"\2\u0097\'\3\2\2\2\u0098\u0099" +
                    "\7\27\2\2\u0099)\3\2\2\2\u009a\u009b\7\30\2\2\u009b+\3\2\2\2\u009c\u00a2" +
                    "\58\35\2\u009d\u00a2\5:\36\2\u009e\u00a2\5<\37\2\u009f\u00a2\5> \2\u00a0" +
                    "\u00a2\5@!\2\u00a1\u009c\3\2\2\2\u00a1\u009d\3\2\2\2\u00a1\u009e\3\2\2" +
                    "\2\u00a1\u009f\3\2\2\2\u00a1\u00a0\3\2\2\2\u00a2-\3\2\2\2\u00a3\u00a6" +
                    "\5\64\33\2\u00a4\u00a6\5\66\34\2\u00a5\u00a3\3\2\2\2\u00a5\u00a4\3\2\2" +
                    "\2\u00a6/\3\2\2\2\u00a7\u00a8\7\r\2\2\u00a8\61\3\2\2\2\u00a9\u00aa\7\16" +
                    "\2\2\u00aa\63\3\2\2\2\u00ab\u00ac\7\25\2\2\u00ac\65\3\2\2\2\u00ad\u00ae" +
                    "\7\26\2\2\u00ae\67\3\2\2\2\u00af\u00b0\7\20\2\2\u00b09\3\2\2\2\u00b1\u00b2" +
                    "\7\21\2\2\u00b2;\3\2\2\2\u00b3\u00b4\7\22\2\2\u00b4=\3\2\2\2\u00b5\u00b6" +
                    "\7\23\2\2\u00b6?\3\2\2\2\u00b7\u00b8\7\24\2\2\u00b8A\3\2\2\2\u00b9\u00ba" +
                    "\7\36\2\2\u00baC\3\2\2\2\bIw|\u0080\u00a1\u00a5";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {
            null, "'/'", "'http'", "'https'", "'?'", "'$filter='", "'$search='", "'''",
            "'contains'", "','", "'startsWith'", "'('", "')'", null, "'eq'", "'gt'",
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
                setState(66);
                scheme();
                setState(67);
                match(SEPERATOR);
                setState(68);
                host();
                setState(71);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == COLON) {
                    {
                        setState(69);
                        match(COLON);
                        setState(70);
                        port();
                    }
                }

                setState(73);
                contextPath();
                setState(74);
                api();
                setState(75);
                functionality();
                setState(76);
                match(T__0);
                setState(77);
                resource();
                setState(78);
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
                setState(80);
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
                setState(82);
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
                setState(84);
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
                setState(86);
                match(T__0);
                setState(87);
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
                setState(89);
                match(T__0);
                setState(90);
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
                setState(92);
                match(T__0);
                setState(93);
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
                setState(95);
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
                setState(97);
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
                setState(99);
                match(T__3);
                setState(100);
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
                setState(102);
                match(T__4);
                setState(103);
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
                setState(105);
                match(T__5);
                setState(106);
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
                setState(108);
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
                setState(117);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__7:
                    case T__9: {
                        setState(110);
                        command();
                    }
                    break;
                    case STRING: {
                        {
                            setState(111);
                            attribute();
                            setState(112);
                            comparator();
                            setState(113);
                            match(T__6);
                            setState(114);
                            value();
                            setState(115);
                            match(T__6);
                        }
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(122);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == AND || _la == OR) {
                    {
                        setState(119);
                        operator();
                        setState(120);
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
                setState(126);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__7: {
                        setState(124);
                        contains();
                    }
                    break;
                    case T__9: {
                        setState(125);
                        startsWith();
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

    public final ContainsContext contains() throws RecognitionException {
        ContainsContext _localctx = new ContainsContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_contains);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(128);
                match(T__7);
                setState(129);
                leftCurlyBracket();
                setState(130);
                attribute();
                setState(131);
                match(T__8);
                setState(132);
                match(T__6);
                setState(133);
                value();
                setState(134);
                match(T__6);
                setState(135);
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

    public final StartsWithContext startsWith() throws RecognitionException {
        StartsWithContext _localctx = new StartsWithContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_startsWith);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(137);
                match(T__9);
                setState(138);
                leftCurlyBracket();
                setState(139);
                attribute();
                setState(140);
                match(T__8);
                setState(141);
                match(T__6);
                setState(142);
                value();
                setState(143);
                match(T__6);
                setState(144);
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

    public final AttributeContext attribute() throws RecognitionException {
        AttributeContext _localctx = new AttributeContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_attribute);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(146);
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
                setState(148);
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

    public final TopContext top() throws RecognitionException {
        TopContext _localctx = new TopContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_top);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(150);
                match(TOP);
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

    public final SkipContext skip() throws RecognitionException {
        SkipContext _localctx = new SkipContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_skip);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(152);
                match(SKIP_);
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
        enterRule(_localctx, 42, RULE_comparator);
        try {
            setState(159);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case EQ:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(154);
                    eq();
                }
                break;
                case GT:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(155);
                    gt();
                }
                break;
                case LT:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(156);
                    lt();
                }
                break;
                case GE:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(157);
                    ge();
                }
                break;
                case LE:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(158);
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
        enterRule(_localctx, 44, RULE_operator);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(163);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case AND: {
                        setState(161);
                        and();
                    }
                    break;
                    case OR: {
                        setState(162);
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
        enterRule(_localctx, 46, RULE_leftCurlyBracket);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(165);
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
        enterRule(_localctx, 48, RULE_rightCurlyBracket);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(167);
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
        enterRule(_localctx, 50, RULE_and);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(169);
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
        enterRule(_localctx, 52, RULE_or);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(171);
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
        enterRule(_localctx, 54, RULE_eq);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(173);
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
        enterRule(_localctx, 56, RULE_gt);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(175);
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
        enterRule(_localctx, 58, RULE_lt);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(177);
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
        enterRule(_localctx, 60, RULE_ge);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(179);
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
        enterRule(_localctx, 62, RULE_le);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(181);
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
        enterRule(_localctx, 64, RULE_string);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(183);
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

    public static class TopContext extends ParserRuleContext {
        public TopContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_top;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterTop(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitTop(this);
        }
    }

    public static class SkipContext extends ParserRuleContext {
        public SkipContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_skip;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).enterSkip(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ODataListener)
                ((ODataListener) listener).exitSkip(this);
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
