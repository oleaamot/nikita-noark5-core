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

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ODataParser extends Parser {
	public static final int
			T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
			T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, WHITESPACE = 16,
			EQ = 17, GT = 18, LT = 19, GE = 20, LE = 21, AND = 22, OR = 23, ASC = 24, DESC = 25, TOP = 26,
			SKIP_ = 27, COUNT = 28, ORDERBY = 29, WS = 30, DIGITS = 31, HEX = 32, STRING = 33, COLON = 34,
			SEPERATOR = 35;
	public static final int
			RULE_odataURL = 0, RULE_scheme = 1, RULE_host = 2, RULE_slash = 3, RULE_contextPath = 4,
			RULE_api = 5, RULE_functionality = 6, RULE_resource = 7, RULE_port = 8,
			RULE_fromContextPath = 9, RULE_odataCommand = 10, RULE_filter = 11, RULE_search = 12,
			RULE_top = 13, RULE_skip = 14, RULE_orderby = 15, RULE_searchCommand = 16,
			RULE_filterCommand = 17, RULE_command = 18, RULE_comparatorCommand = 19,
			RULE_contains = 20, RULE_startsWith = 21, RULE_attribute = 22, RULE_value = 23,
			RULE_sortOrder = 24, RULE_comparator = 25, RULE_operator = 26, RULE_leftCurlyBracket = 27,
			RULE_rightCurlyBracket = 28, RULE_and = 29, RULE_or = 30, RULE_eq = 31,
			RULE_gt = 32, RULE_lt = 33, RULE_ge = 34, RULE_le = 35, RULE_string = 36,
			RULE_number = 37, RULE_asc = 38, RULE_desc = 39;
	public static final String[] ruleNames = {
			"odataURL", "scheme", "host", "slash", "contextPath", "api", "functionality",
			"resource", "port", "fromContextPath", "odataCommand", "filter", "search",
			"top", "skip", "orderby", "searchCommand", "filterCommand", "command",
			"comparatorCommand", "contains", "startsWith", "attribute", "value", "sortOrder",
			"comparator", "operator", "leftCurlyBracket", "rightCurlyBracket", "and",
			"or", "eq", "gt", "lt", "ge", "le", "string", "number", "asc", "desc"
	};
	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	public static final String _serializedATN =
			"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3%\u00ee\4\2\t\2\4" +
					"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
					"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
					"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
					"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!" +
					"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\3\2\3\2\3\2\3" +
					"\2\3\2\5\2X\n\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3" +
					"\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13" +
					"\3\f\3\f\3\f\3\f\7\f{\n\f\f\f\16\f~\13\f\3\r\3\r\3\r\3\16\3\16\3\16\3" +
					"\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\5\21\u008f\n\21\3\21\3\21" +
					"\5\21\u0093\n\21\7\21\u0095\n\21\f\21\16\21\u0098\13\21\3\22\3\22\3\23" +
					"\3\23\5\23\u009e\n\23\3\23\3\23\3\23\5\23\u00a3\n\23\3\24\3\24\5\24\u00a7" +
					"\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26" +
					"\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\31" +
					"\3\31\3\32\3\32\5\32\u00c7\n\32\3\33\3\33\3\33\3\33\3\33\5\33\u00ce\n" +
					"\33\3\34\3\34\5\34\u00d2\n\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3" +
					"!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\2\2*\2\4\6\b" +
					"\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNP\2\3" +
					"\3\2\3\4\2\u00d6\2R\3\2\2\2\4[\3\2\2\2\6]\3\2\2\2\b_\3\2\2\2\na\3\2\2" +
					"\2\fd\3\2\2\2\16g\3\2\2\2\20j\3\2\2\2\22l\3\2\2\2\24n\3\2\2\2\26|\3\2" +
					"\2\2\30\177\3\2\2\2\32\u0082\3\2\2\2\34\u0085\3\2\2\2\36\u0088\3\2\2\2" +
					" \u008b\3\2\2\2\"\u0099\3\2\2\2$\u009d\3\2\2\2&\u00a6\3\2\2\2(\u00a8\3" +
					"\2\2\2*\u00ae\3\2\2\2,\u00b7\3\2\2\2.\u00c0\3\2\2\2\60\u00c2\3\2\2\2\62" +
					"\u00c6\3\2\2\2\64\u00cd\3\2\2\2\66\u00d1\3\2\2\28\u00d3\3\2\2\2:\u00d5" +
					"\3\2\2\2<\u00d7\3\2\2\2>\u00d9\3\2\2\2@\u00db\3\2\2\2B\u00dd\3\2\2\2D" +
					"\u00df\3\2\2\2F\u00e1\3\2\2\2H\u00e3\3\2\2\2J\u00e5\3\2\2\2L\u00e7\3\2" +
					"\2\2N\u00e9\3\2\2\2P\u00eb\3\2\2\2RS\5\4\3\2ST\7%\2\2TW\5\6\4\2UV\7$\2" +
					"\2VX\5\22\n\2WU\3\2\2\2WX\3\2\2\2XY\3\2\2\2YZ\5\24\13\2Z\3\3\2\2\2[\\" +
					"\t\2\2\2\\\5\3\2\2\2]^\5J&\2^\7\3\2\2\2_`\7\5\2\2`\t\3\2\2\2ab\7\5\2\2" +
					"bc\5J&\2c\13\3\2\2\2de\7\5\2\2ef\5J&\2f\r\3\2\2\2gh\7\5\2\2hi\5J&\2i\17" +
					"\3\2\2\2jk\5J&\2k\21\3\2\2\2lm\7!\2\2m\23\3\2\2\2no\5\n\6\2op\5\f\7\2" +
					"pq\5\16\b\2qr\7\5\2\2rs\5\20\t\2st\7\6\2\2tu\5\26\f\2u\25\3\2\2\2v{\5" +
					"\30\r\2w{\5\34\17\2x{\5\36\20\2y{\5 \21\2zv\3\2\2\2zw\3\2\2\2zx\3\2\2" +
					"\2zy\3\2\2\2{~\3\2\2\2|z\3\2\2\2|}\3\2\2\2}\27\3\2\2\2~|\3\2\2\2\177\u0080" +
					"\7\7\2\2\u0080\u0081\5$\23\2\u0081\31\3\2\2\2\u0082\u0083\7\b\2\2\u0083" +
					"\u0084\5\"\22\2\u0084\33\3\2\2\2\u0085\u0086\7\t\2\2\u0086\u0087\5L\'" +
					"\2\u0087\35\3\2\2\2\u0088\u0089\7\n\2\2\u0089\u008a\5L\'\2\u008a\37\3" +
					"\2\2\2\u008b\u008c\7\13\2\2\u008c\u008e\5.\30\2\u008d\u008f\5\62\32\2" +
					"\u008e\u008d\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0096\3\2\2\2\u0090\u0092" +
					"\5.\30\2\u0091\u0093\5\62\32\2\u0092\u0091\3\2\2\2\u0092\u0093\3\2\2\2" +
					"\u0093\u0095\3\2\2\2\u0094\u0090\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094" +
					"\3\2\2\2\u0096\u0097\3\2\2\2\u0097!\3\2\2\2\u0098\u0096\3\2\2\2\u0099" +
					"\u009a\5J&\2\u009a#\3\2\2\2\u009b\u009e\5&\24\2\u009c\u009e\5(\25\2\u009d" +
					"\u009b\3\2\2\2\u009d\u009c\3\2\2\2\u009e\u00a2\3\2\2\2\u009f\u00a0\5\66" +
					"\34\2\u00a0\u00a1\5$\23\2\u00a1\u00a3\3\2\2\2\u00a2\u009f\3\2\2\2\u00a2" +
					"\u00a3\3\2\2\2\u00a3%\3\2\2\2\u00a4\u00a7\5*\26\2\u00a5\u00a7\5,\27\2" +
					"\u00a6\u00a4\3\2\2\2\u00a6\u00a5\3\2\2\2\u00a7\'\3\2\2\2\u00a8\u00a9\5" +
					".\30\2\u00a9\u00aa\5\64\33\2\u00aa\u00ab\7\f\2\2\u00ab\u00ac\5\60\31\2" +
					"\u00ac\u00ad\7\f\2\2\u00ad)\3\2\2\2\u00ae\u00af\7\r\2\2\u00af\u00b0\5" +
					"8\35\2\u00b0\u00b1\5.\30\2\u00b1\u00b2\7\16\2\2\u00b2\u00b3\7\f\2\2\u00b3" +
					"\u00b4\5\60\31\2\u00b4\u00b5\7\f\2\2\u00b5\u00b6\5:\36\2\u00b6+\3\2\2" +
					"\2\u00b7\u00b8\7\17\2\2\u00b8\u00b9\58\35\2\u00b9\u00ba\5.\30\2\u00ba" +
					"\u00bb\7\16\2\2\u00bb\u00bc\7\f\2\2\u00bc\u00bd\5\60\31\2\u00bd\u00be" +
					"\7\f\2\2\u00be\u00bf\5:\36\2\u00bf-\3\2\2\2\u00c0\u00c1\5J&\2\u00c1/\3" +
					"\2\2\2\u00c2\u00c3\5J&\2\u00c3\61\3\2\2\2\u00c4\u00c7\5N(\2\u00c5\u00c7" +
					"\5P)\2\u00c6\u00c4\3\2\2\2\u00c6\u00c5\3\2\2\2\u00c7\63\3\2\2\2\u00c8" +
					"\u00ce\5@!\2\u00c9\u00ce\5B\"\2\u00ca\u00ce\5D#\2\u00cb\u00ce\5F$\2\u00cc" +
					"\u00ce\5H%\2\u00cd\u00c8\3\2\2\2\u00cd\u00c9\3\2\2\2\u00cd\u00ca\3\2\2" +
					"\2\u00cd\u00cb\3\2\2\2\u00cd\u00cc\3\2\2\2\u00ce\65\3\2\2\2\u00cf\u00d2" +
					"\5<\37\2\u00d0\u00d2\5> \2\u00d1\u00cf\3\2\2\2\u00d1\u00d0\3\2\2\2\u00d2" +
					"\67\3\2\2\2\u00d3\u00d4\7\20\2\2\u00d49\3\2\2\2\u00d5\u00d6\7\21\2\2\u00d6" +
					";\3\2\2\2\u00d7\u00d8\7\30\2\2\u00d8=\3\2\2\2\u00d9\u00da\7\31\2\2\u00da" +
					"?\3\2\2\2\u00db\u00dc\7\23\2\2\u00dcA\3\2\2\2\u00dd\u00de\7\24\2\2\u00de" +
					"C\3\2\2\2\u00df\u00e0\7\25\2\2\u00e0E\3\2\2\2\u00e1\u00e2\7\26\2\2\u00e2" +
					"G\3\2\2\2\u00e3\u00e4\7\27\2\2\u00e4I\3\2\2\2\u00e5\u00e6\7#\2\2\u00e6" +
					"K\3\2\2\2\u00e7\u00e8\7!\2\2\u00e8M\3\2\2\2\u00e9\u00ea\7\32\2\2\u00ea" +
					"O\3\2\2\2\u00eb\u00ec\7\33\2\2\u00ecQ\3\2\2\2\16Wz|\u008e\u0092\u0096" +
					"\u009d\u00a2\u00a6\u00c6\u00cd\u00d1";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
			new PredictionContextCache();
	private static final String[] _LITERAL_NAMES = {
			null, "'http'", "'https'", "'/'", "'?'", "'$filter='", "'$search='", "'$top='",
			"'$skip='", "'$orderby='", "'''", "'contains'", "','", "'startsWith'",
			"'('", "')'", null, "'eq'", "'gt'", "'lt'", "'ge'", "'le'", "'and'", "'or'",
			"'asc'", "'desc'", "'top'", "'skip'", "'count'", "'orderby'", null, null,
			null, null, "':'", "'://'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
			null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, "WHITESPACE", "EQ", "GT", "LT", "GE", "LE", "AND",
			"OR", "ASC", "DESC", "TOP", "SKIP_", "COUNT", "ORDERBY", "WS", "DIGITS",
			"HEX", "STRING", "COLON", "SEPERATOR"
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
				setState(80);
				scheme();
				setState(81);
				match(SEPERATOR);
				setState(82);
				host();
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == COLON) {
					{
						setState(83);
						match(COLON);
						setState(84);
						port();
					}
				}

				setState(87);
				fromContextPath();
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
				setState(89);
				_la = _input.LA(1);
				if (!(_la == T__0 || _la == T__1)) {
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

	public final SlashContext slash() throws RecognitionException {
		SlashContext _localctx = new SlashContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_slash);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(93);
				match(T__2);
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
				setState(95);
				match(T__2);
				setState(96);
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
				setState(98);
				match(T__2);
				setState(99);
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
				setState(101);
				match(T__2);
				setState(102);
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

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_port);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(106);
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

	public final FromContextPathContext fromContextPath() throws RecognitionException {
		FromContextPathContext _localctx = new FromContextPathContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_fromContextPath);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(108);
				contextPath();
				setState(109);
				api();
				setState(110);
				functionality();
				setState(111);
				match(T__2);
				setState(112);
				resource();
				setState(113);
				match(T__3);
				setState(114);
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

	public final OdataCommandContext odataCommand() throws RecognitionException {
		OdataCommandContext _localctx = new OdataCommandContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_odataCommand);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__6) | (1L << T__7) | (1L << T__8))) != 0)) {
					{
						setState(120);
						_errHandler.sync(this);
						switch (_input.LA(1)) {
							case T__4: {
								setState(116);
								filter();
							}
							break;
							case T__6: {
								setState(117);
								top();
							}
							break;
							case T__7: {
								setState(118);
								skip();
							}
							break;
							case T__8: {
								setState(119);
								orderby();
							}
							break;
							default:
								throw new NoViableAltException(this);
						}
					}
					setState(124);
					_errHandler.sync(this);
					_la = _input.LA(1);
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

	public final FilterContext filter() throws RecognitionException {
		FilterContext _localctx = new FilterContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_filter);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(125);
				match(T__4);
				setState(126);
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
		enterRule(_localctx, 24, RULE_search);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(128);
				match(T__5);
				setState(129);
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

	public final TopContext top() throws RecognitionException {
		TopContext _localctx = new TopContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_top);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(131);
				match(T__6);
				setState(132);
				number();
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
		enterRule(_localctx, 28, RULE_skip);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(134);
				match(T__7);
				setState(135);
				number();
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

	public final OrderbyContext orderby() throws RecognitionException {
		OrderbyContext _localctx = new OrderbyContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_orderby);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(137);
				match(T__8);
				setState(138);
				attribute();
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == ASC || _la == DESC) {
					{
						setState(139);
						sortOrder();
					}
				}

				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == STRING) {
					{
						{
							setState(142);
							attribute();
							setState(144);
							_errHandler.sync(this);
							_la = _input.LA(1);
							if (_la == ASC || _la == DESC) {
								{
									setState(143);
									sortOrder();
								}
							}

						}
					}
					setState(150);
					_errHandler.sync(this);
					_la = _input.LA(1);
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

	public final SearchCommandContext searchCommand() throws RecognitionException {
		SearchCommandContext _localctx = new SearchCommandContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_searchCommand);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(151);
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
		enterRule(_localctx, 34, RULE_filterCommand);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(155);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case T__10:
					case T__12: {
						setState(153);
						command();
					}
					break;
					case STRING: {
						setState(154);
						comparatorCommand();
					}
					break;
					default:
						throw new NoViableAltException(this);
				}
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == AND || _la == OR) {
					{
						setState(157);
						operator();
						setState(158);
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
		enterRule(_localctx, 36, RULE_command);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(164);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case T__10: {
						setState(162);
						contains();
					}
					break;
					case T__12: {
						setState(163);
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

	public final ComparatorCommandContext comparatorCommand() throws RecognitionException {
		ComparatorCommandContext _localctx = new ComparatorCommandContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_comparatorCommand);
		try {
			enterOuterAlt(_localctx, 1);
			{
				{
					setState(166);
					attribute();
					setState(167);
					comparator();
					setState(168);
					match(T__9);
					setState(169);
					value();
					setState(170);
					match(T__9);
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
		enterRule(_localctx, 40, RULE_contains);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(172);
				match(T__10);
				setState(173);
				leftCurlyBracket();
				setState(174);
				attribute();
				setState(175);
				match(T__11);
				setState(176);
				match(T__9);
				setState(177);
				value();
				setState(178);
				match(T__9);
				setState(179);
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
		enterRule(_localctx, 42, RULE_startsWith);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(181);
				match(T__12);
				setState(182);
				leftCurlyBracket();
				setState(183);
				attribute();
				setState(184);
				match(T__11);
				setState(185);
				match(T__9);
				setState(186);
				value();
				setState(187);
				match(T__9);
				setState(188);
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
		enterRule(_localctx, 44, RULE_attribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(190);
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
		enterRule(_localctx, 46, RULE_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(192);
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

	public final SortOrderContext sortOrder() throws RecognitionException {
		SortOrderContext _localctx = new SortOrderContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_sortOrder);
		try {
			setState(196);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case ASC:
					enterOuterAlt(_localctx, 1);
				{
					setState(194);
					asc();
				}
				break;
				case DESC:
					enterOuterAlt(_localctx, 2);
				{
					setState(195);
					desc();
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

	public final ComparatorContext comparator() throws RecognitionException {
		ComparatorContext _localctx = new ComparatorContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_comparator);
		try {
			setState(203);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case EQ:
					enterOuterAlt(_localctx, 1);
				{
					setState(198);
					eq();
				}
				break;
				case GT:
					enterOuterAlt(_localctx, 2);
				{
					setState(199);
					gt();
				}
				break;
				case LT:
					enterOuterAlt(_localctx, 3);
				{
					setState(200);
					lt();
				}
				break;
				case GE:
					enterOuterAlt(_localctx, 4);
				{
					setState(201);
					ge();
				}
				break;
				case LE:
					enterOuterAlt(_localctx, 5);
				{
					setState(202);
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
		enterRule(_localctx, 52, RULE_operator);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(207);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case AND: {
						setState(205);
						and();
					}
					break;
					case OR: {
						setState(206);
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
		enterRule(_localctx, 54, RULE_leftCurlyBracket);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(209);
				match(T__13);
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
		enterRule(_localctx, 56, RULE_rightCurlyBracket);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(211);
				match(T__14);
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
		enterRule(_localctx, 58, RULE_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(213);
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
		enterRule(_localctx, 60, RULE_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(215);
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
		enterRule(_localctx, 62, RULE_eq);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(217);
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
		enterRule(_localctx, 64, RULE_gt);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(219);
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
		enterRule(_localctx, 66, RULE_lt);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(221);
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
		enterRule(_localctx, 68, RULE_ge);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(223);
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
		enterRule(_localctx, 70, RULE_le);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(225);
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
		enterRule(_localctx, 72, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(227);
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

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(229);
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

	public final AscContext asc() throws RecognitionException {
		AscContext _localctx = new AscContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_asc);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(231);
				match(ASC);
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

	public final DescContext desc() throws RecognitionException {
		DescContext _localctx = new DescContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_desc);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(233);
				match(DESC);
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

		public FromContextPathContext fromContextPath() {
			return getRuleContext(FromContextPathContext.class, 0);
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

	public static class FromContextPathContext extends ParserRuleContext {
		public FromContextPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
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

		@Override
		public int getRuleIndex() {
			return RULE_fromContextPath;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).enterFromContextPath(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).exitFromContextPath(this);
		}
	}

	public static class OdataCommandContext extends ParserRuleContext {
		public OdataCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public List<FilterContext> filter() {
			return getRuleContexts(FilterContext.class);
		}

		public FilterContext filter(int i) {
			return getRuleContext(FilterContext.class, i);
		}

		public List<TopContext> top() {
			return getRuleContexts(TopContext.class);
		}

		public TopContext top(int i) {
			return getRuleContext(TopContext.class, i);
		}

		public List<SkipContext> skip() {
			return getRuleContexts(SkipContext.class);
		}

		public SkipContext skip(int i) {
			return getRuleContext(SkipContext.class, i);
		}

		public List<OrderbyContext> orderby() {
			return getRuleContexts(OrderbyContext.class);
		}

		public OrderbyContext orderby(int i) {
			return getRuleContext(OrderbyContext.class, i);
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

	public static class TopContext extends ParserRuleContext {
		public TopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public NumberContext number() {
			return getRuleContext(NumberContext.class, 0);
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

		public NumberContext number() {
			return getRuleContext(NumberContext.class, 0);
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

	public static class OrderbyContext extends ParserRuleContext {
		public OrderbyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}

		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class, i);
		}

		public List<SortOrderContext> sortOrder() {
			return getRuleContexts(SortOrderContext.class);
		}

		public SortOrderContext sortOrder(int i) {
			return getRuleContext(SortOrderContext.class, i);
		}

		@Override
		public int getRuleIndex() {
			return RULE_orderby;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).enterOrderby(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).exitOrderby(this);
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

		public ComparatorCommandContext comparatorCommand() {
			return getRuleContext(ComparatorCommandContext.class, 0);
		}

		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class, 0);
		}

		public FilterCommandContext filterCommand() {
			return getRuleContext(FilterCommandContext.class, 0);
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

	public static class ComparatorCommandContext extends ParserRuleContext {
		public ComparatorCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
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
			return RULE_comparatorCommand;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).enterComparatorCommand(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).exitComparatorCommand(this);
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

	public static class SortOrderContext extends ParserRuleContext {
		public SortOrderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public AscContext asc() {
			return getRuleContext(AscContext.class, 0);
		}

		public DescContext desc() {
			return getRuleContext(DescContext.class, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_sortOrder;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).enterSortOrder(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).exitSortOrder(this);
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

	public static class NumberContext extends ParserRuleContext {
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode DIGITS() {
			return getToken(ODataParser.DIGITS, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_number;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).enterNumber(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).exitNumber(this);
		}
	}

	public static class AscContext extends ParserRuleContext {
		public AscContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode ASC() {
			return getToken(ODataParser.ASC, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_asc;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).enterAsc(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).exitAsc(this);
		}
	}

	public static class DescContext extends ParserRuleContext {
		public DescContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode DESC() {
			return getToken(ODataParser.DESC, 0);
		}

		@Override
		public int getRuleIndex() {
			return RULE_desc;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).enterDesc(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof ODataListener)
				((ODataListener) listener).exitDesc(this);
		}
	}
}
