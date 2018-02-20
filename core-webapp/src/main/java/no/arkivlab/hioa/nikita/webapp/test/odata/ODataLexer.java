// Generated from /home/tsodring/git/nikita-noark5-core/core-webapp/src/main/resources/odata/OData.g4 by ANTLR 4.7
package no.arkivlab.hioa.nikita.webapp.test.odata;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ODataLexer extends Lexer {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, WHITESPACE = 12, EQ = 13, GT = 14, LT = 15, GE = 16, LE = 17, AND = 18,
            OR = 19, TOP = 20, SKIP_ = 21, COUNT = 22, ORDERBY = 23, WS = 24, DIGITS = 25, HEX = 26,
            STRING = 27, COLON = 28, SEPERATOR = 29;
    public static final String[] ruleNames = {
            "T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
            "T__9", "T__10", "WHITESPACE", "EQ", "GT", "LT", "GE", "LE", "AND", "OR",
            "TOP", "SKIP_", "COUNT", "ORDERBY", "WS", "DIGITS", "HEX", "STRING", "COLON",
            "SEPERATOR"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\37\u00c7\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\2\3" +
                    "\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6" +
                    "\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3" +
                    "\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3" +
                    "\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3" +
                    "\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3" +
                    "\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3" +
                    "\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\6\31\u00ad" +
                    "\n\31\r\31\16\31\u00ae\3\32\6\32\u00b2\n\32\r\32\16\32\u00b3\3\33\3\33" +
                    "\3\33\6\33\u00b9\n\33\r\33\16\33\u00ba\3\34\6\34\u00be\n\34\r\34\16\34" +
                    "\u00bf\3\35\3\35\3\36\3\36\3\36\3\36\2\2\37\3\3\5\4\7\5\t\6\13\7\r\b\17" +
                    "\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+" +
                    "\27-\30/\31\61\32\63\33\65\34\67\359\36;\37\3\2\5\3\2\62;\5\2\62;CHch" +
                    "\6\2\62;C\\c|\u0080\u0080\2\u00ca\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2" +
                    "\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3" +
                    "\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2" +
                    "\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2" +
                    "\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2" +
                    "\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\3=\3\2\2\2\5B\3\2\2\2\7H\3\2\2" +
                    "\2\tJ\3\2\2\2\13L\3\2\2\2\rU\3\2\2\2\17^\3\2\2\2\21`\3\2\2\2\23l\3\2\2" +
                    "\2\25n\3\2\2\2\27p\3\2\2\2\31z\3\2\2\2\33~\3\2\2\2\35\u0081\3\2\2\2\37" +
                    "\u0084\3\2\2\2!\u0087\3\2\2\2#\u008a\3\2\2\2%\u008d\3\2\2\2\'\u0091\3" +
                    "\2\2\2)\u0094\3\2\2\2+\u0098\3\2\2\2-\u009d\3\2\2\2/\u00a3\3\2\2\2\61" +
                    "\u00ac\3\2\2\2\63\u00b1\3\2\2\2\65\u00b8\3\2\2\2\67\u00bd\3\2\2\29\u00c1" +
                    "\3\2\2\2;\u00c3\3\2\2\2=>\7j\2\2>?\7v\2\2?@\7v\2\2@A\7r\2\2A\4\3\2\2\2" +
                    "BC\7j\2\2CD\7v\2\2DE\7v\2\2EF\7r\2\2FG\7u\2\2G\6\3\2\2\2HI\7\61\2\2I\b" +
                    "\3\2\2\2JK\7A\2\2K\n\3\2\2\2LM\7&\2\2MN\7h\2\2NO\7k\2\2OP\7n\2\2PQ\7v" +
                    "\2\2QR\7g\2\2RS\7t\2\2ST\7?\2\2T\f\3\2\2\2UV\7&\2\2VW\7u\2\2WX\7g\2\2" +
                    "XY\7c\2\2YZ\7t\2\2Z[\7e\2\2[\\\7j\2\2\\]\7?\2\2]\16\3\2\2\2^_\7)\2\2_" +
                    "\20\3\2\2\2`a\7u\2\2ab\7v\2\2bc\7c\2\2cd\7t\2\2de\7v\2\2ef\7u\2\2fg\7" +
                    "Y\2\2gh\7k\2\2hi\7v\2\2ij\7j\2\2jk\7*\2\2k\22\3\2\2\2lm\7.\2\2m\24\3\2" +
                    "\2\2no\7+\2\2o\26\3\2\2\2pq\7e\2\2qr\7q\2\2rs\7p\2\2st\7v\2\2tu\7c\2\2" +
                    "uv\7k\2\2vw\7p\2\2wx\7u\2\2xy\7*\2\2y\30\3\2\2\2z{\7\"\2\2{|\3\2\2\2|" +
                    "}\b\r\2\2}\32\3\2\2\2~\177\7g\2\2\177\u0080\7s\2\2\u0080\34\3\2\2\2\u0081" +
                    "\u0082\7i\2\2\u0082\u0083\7v\2\2\u0083\36\3\2\2\2\u0084\u0085\7n\2\2\u0085" +
                    "\u0086\7v\2\2\u0086 \3\2\2\2\u0087\u0088\7i\2\2\u0088\u0089\7g\2\2\u0089" +
                    "\"\3\2\2\2\u008a\u008b\7n\2\2\u008b\u008c\7g\2\2\u008c$\3\2\2\2\u008d" +
                    "\u008e\7c\2\2\u008e\u008f\7p\2\2\u008f\u0090\7f\2\2\u0090&\3\2\2\2\u0091" +
                    "\u0092\7q\2\2\u0092\u0093\7t\2\2\u0093(\3\2\2\2\u0094\u0095\7v\2\2\u0095" +
                    "\u0096\7q\2\2\u0096\u0097\7r\2\2\u0097*\3\2\2\2\u0098\u0099\7u\2\2\u0099" +
                    "\u009a\7m\2\2\u009a\u009b\7k\2\2\u009b\u009c\7r\2\2\u009c,\3\2\2\2\u009d" +
                    "\u009e\7e\2\2\u009e\u009f\7q\2\2\u009f\u00a0\7w\2\2\u00a0\u00a1\7p\2\2" +
                    "\u00a1\u00a2\7v\2\2\u00a2.\3\2\2\2\u00a3\u00a4\7q\2\2\u00a4\u00a5\7t\2" +
                    "\2\u00a5\u00a6\7f\2\2\u00a6\u00a7\7g\2\2\u00a7\u00a8\7t\2\2\u00a8\u00a9" +
                    "\7d\2\2\u00a9\u00aa\7{\2\2\u00aa\60\3\2\2\2\u00ab\u00ad\7\"\2\2\u00ac" +
                    "\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00af\3\2" +
                    "\2\2\u00af\62\3\2\2\2\u00b0\u00b2\t\2\2\2\u00b1\u00b0\3\2\2\2\u00b2\u00b3" +
                    "\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\64\3\2\2\2\u00b5" +
                    "\u00b6\7\'\2\2\u00b6\u00b7\t\3\2\2\u00b7\u00b9\t\3\2\2\u00b8\u00b5\3\2" +
                    "\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb" +
                    "\66\3\2\2\2\u00bc\u00be\t\4\2\2\u00bd\u00bc\3\2\2\2\u00be\u00bf\3\2\2" +
                    "\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c08\3\2\2\2\u00c1\u00c2" +
                    "\7<\2\2\u00c2:\3\2\2\2\u00c3\u00c4\7<\2\2\u00c4\u00c5\7\61\2\2\u00c5\u00c6" +
                    "\7\61\2\2\u00c6<\3\2\2\2\7\2\u00ae\u00b3\u00ba\u00bf\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {
            null, "'http'", "'https'", "'/'", "'?'", "'$filter='", "'$search='", "'''",
            "'startsWith('", "','", "')'", "'contains('", "' '", "'eq'", "'gt'", "'lt'",
            "'ge'", "'le'", "'and'", "'or'", "'top'", "'skip'", "'count'", "'orderby'",
            null, null, null, null, "':'", "'://'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, null, null, null, null, null, null, null, null,
            "WHITESPACE", "EQ", "GT", "LT", "GE", "LE", "AND", "OR", "TOP", "SKIP_",
            "COUNT", "ORDERBY", "WS", "DIGITS", "HEX", "STRING", "COLON", "SEPERATOR"
    };
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

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

    public ODataLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}
