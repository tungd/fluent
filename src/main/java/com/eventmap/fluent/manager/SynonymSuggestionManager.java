package com.eventmap.fluent.manager;



import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import com.eventmap.fluent.domain.Match;
import com.eventmap.fluent.domain.Matches;

import edu.smu.tspell.wordnet.AdjectiveSynset;
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.VerbSynset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

@Component
@EnableAutoConfiguration
public class SynonymSuggestionManager {
	List<String> verbNodeNames = Arrays.asList(new String[]{"VB","VBD","VBG","VBN","VBP","VBZ"});
	List<String> adjNodenames = Arrays.asList(new String[]{"JJ","JJR","JJS"});
	List<String> ignoreToBe = Arrays.asList(new String[]{"is","are","am","be"});
	List<Word> verbList;
	List<Word> adjList;
	Map<Integer,String> compoundWord;

	public Matches suggestWordProcess(String text) {
		compoundWord = new HashMap<Integer, String>();
	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    LexicalizedParser lp = LexicalizedParser.loadModel(            "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz" );
	    lp.setOptionFlags(new String[]{"-maxLength", "500", "-retainTmpSubcategories"});
	    TokenizerFactory tokenizerFactory = PTBTokenizer.factory( new CoreLabelTokenFactory(), "");
	    List wordList = tokenizerFactory.getTokenizer(new StringReader(text)).tokenize();
	    Tree tree = lp.apply(wordList);
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
	    System.out.println("job Start");
	    Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
	    System.out.println(tdl);
//	    List<TypedDependency> listType = new ArrayList<TypedDependency>(tdl);
	    for (TypedDependency typedDep : tdl) {
//	    	System.out.println(typedDep.reln());
	    	if ("compound:prt".equals(typedDep.reln().toString())) {
		    	System.out.println(typedDep.gov().word());
		    	System.out.println(typedDep.gov().beginPosition());
		    	String combineWord= typedDep.gov().word()+" "+typedDep.dep().word();
		    	compoundWord.put(typedDep.gov().beginPosition(), combineWord);
	    	}
	    }
	    System.out.println(tree);
		verbList = new ArrayList<Word>();
		adjList = new ArrayList<Word>();
//		Properties props = new Properties();
//
//		props.setProperty("annotators", "tokenize, ssplit, pos, parse");
//		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// read some text in the text variable
//		String text1 = "I have had a dog. I usually take it out for a walk"; // Add your text here!

		// create an empty Annotation just with the given text
//		Annotation document = new Annotation(text);

		// run all Annotators on this text
//		pipeline.annotate(document);

//		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

//		for(CoreMap sentence: sentences) {
			  // traversing the words in the current sentence
			  // a CoreLabel is a CoreMap with additional token-specific methods
//			  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
//			    // this is the text of the token
//			    String word = token.get(TextAnnotation.class);
//			    System.out.print(word );
//			    // this is the POS tag of the token
//			    String pos = token.get(PartOfSpeechAnnotation.class);
//			    System.out.print(pos + " ");
//			    // this is the NER label of the token
//			    String ne = token.get(NamedEntityTagAnnotation.class);
////			    System.out.println(ne);
//			  }

			  // this is the parse tree of the current sentence
//			  System.out.println("");
//			  Tree tree = sentence.get(TreeAnnotation.class);
//			  System.out.println(tree);
			  extractPhrasesFromString(tree,text);

			  // this is the Stanford dependency graph of the current sentence
//			  SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//			}
		Matches matchList = new Matches();
		for (Word verb: verbList) {
			
			
			int position = text.substring(0, verb.beginPosition()).split(" ").length;
			System.out.println("SubString: "+text.substring(0, verb.beginPosition())+ " Begin position"+position + " Column position:" +verb.beginPosition());
			Match match= synonymSuggestion(verb.word(), "VP",position);
			if (match!=null)
				matchList.getMatches().add(match);
		}
		for (Word adj: adjList) {
//			System.out.println(text.substring(0, adj.beginPosition()));
			int position = text.substring(0, adj.beginPosition()).split(" ").length;
			System.out.println("SubString: "+text.substring(0, adj.beginPosition())+ " Begin position"+position + " Column position:" +adj.beginPosition());
			Match match= synonymSuggestion(adj.word(),"ADJ",position);
			if (match!=null)
				matchList.getMatches().add(match);
		}
		System.out.println("Finish");
		return matchList;

	}

	public  List<String> extractPhrasesFromString(Tree tree, String originalString) {
	    List<String> foundPhraseNodes = new ArrayList<String>();

	    collect(tree, foundPhraseNodes);
//	    logger.debug("parsing " + originalString + " yields " + foundPhraseNodes.size() + " noun node(s).");

	    return  foundPhraseNodes;
	}

	private void collect(Tree tree, List<String> foundPhraseNodes) {
	    if (tree == null || tree.isLeaf()) {
	        return;
	    }

//	    nounNodeNames.add( "NNPS");


	    Label label = tree.label();
	    if (label instanceof CoreLabel) {
	        CoreLabel coreLabel = ((CoreLabel) label);

	        String text = ((CoreLabel) label).getString(CoreAnnotations.OriginalTextAnnotation.class);
//	        if (text.equals("THE")) {
//	            System.out.println(" got THE text: " + text);
//	        }

	        String category = coreLabel.getString(CoreAnnotations.CategoryAnnotation.class);
	        if (verbNodeNames.contains(category)) {
	        	List<Word> words = tree.yieldWords();
	        	for (Word word: words){
	        		if (ignoreToBe.contains(word.word()))
	        			continue;
                    System.out.print(String.format("(%s - "+category+" "+word.beginPosition()+ "),",word.word()));
                    String combineWord = compoundWord.get(word.beginPosition());
                    if (combineWord!=null) {
                    	Word newWord = new Word();
                    	newWord.setWord(combineWord);
                    	newWord.setBeginPosition(word.beginPosition());
                    	verbList.add(newWord);
                    }else {
                    	verbList.add(word);
                    }
	        	}

//	        	System.out.println(leaves);
//	        	for (Tree leaf : leaves){
//	                List<Word> words = leaf.yieldWords();
//	                for (Word word: words)
//	                    System.out.print(String.format("(%s - "+category+"),",word.word()));
//	              }
	        }
	        if (adjNodenames.contains(category)) {
	        	List<Word> words = tree.yieldWords();
	        	for (Word word: words){
                    System.out.print(String.format("(%s - "+category+" "+word.beginPosition()+ "),",word.word()));
                    adjList.add(word);
	        	}
	        }
	    }


	    List<Tree> kids = tree.getChildrenAsList();
	    for (Tree kid : kids) {
	        collect(kid, foundPhraseNodes);
	    }
	}

	public Match synonymSuggestion(String word, String type, Integer position) {
		Match match = new Match();
		System.out.println("Start Process word: "+word +"\n");
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		System.setProperty("wordnet.database.dir", classloader.getResource("dict").getPath());
		NounSynset nounSynset;
		NounSynset[] hyponyms;
		AdjectiveSynset adjSyn;
		AdjectiveSynset[] adjHynponyms;
		VerbSynset verbSynset;
		VerbSynset[] verbHynponyms;
		String suggestMessage="Synonym:";

		WordNetDatabase database = WordNetDatabase.getFileInstance();
		SynsetType wordType = null;
		if (type.equals("NP")) {
			wordType = SynsetType.NOUN;
			Synset[] synsets = database.getSynsets(word, wordType);
			for (int i = 0; i < synsets.length; i++) {
			    nounSynset = (NounSynset)(synsets[i]);
			    hyponyms = nounSynset.getHyponyms();
			    System.out.println(nounSynset.getWordForms()[0] +
			            ": " + nounSynset.getDefinition() + ") has " + hyponyms.length + " hyponyms");
			}
		} else if (type.equals("ADJ")) {
			wordType = SynsetType.ADJECTIVE;
			Synset[] synsets = database.getSynsets(word, wordType);
			for (int i = 0; i < synsets.length; i++) {
				adjSyn = (AdjectiveSynset)(synsets[i]);
				adjHynponyms = adjSyn.getSimilar();
				System.out.println(adjSyn.getWordForms()[0] +
			            ": " + adjSyn.getDefinition() + ") has " + adjHynponyms.length + " similar");
				String defini = adjSyn.getDefinition();
				String listSyn="";
				if (adjHynponyms.length >0) {
					AdjectiveSynset set = adjHynponyms[0];
					String [] listSimilar = set.getWordForms();
			    	for (String list: listSimilar) {
			    		listSyn=listSyn+ list+",";
			    	}
				}
		    suggestMessage =suggestMessage + adjSyn.getWordForms()[0]+ ":" + adjSyn.getDefinition() +"\n Synonym:"+ listSyn+ "\n \n";
		    if (i>=3)
		    	break;
			}
		} else if (type.equals("VP")) {
			wordType = SynsetType.VERB;
			Synset[] synsets = database.getSynsets(word, wordType);
			for (int i = 0; i < synsets.length; i++) {
				verbSynset = (VerbSynset)(synsets[i]);
				verbHynponyms = verbSynset.getVerbGroup();
			    System.out.println(verbSynset.toString() +
			            ": " + verbSynset.getDefinition() + ") has " + verbHynponyms.length + " hyponyms");
			    String defini = verbSynset.getDefinition();
			    String listSyn="";
			    if (verbHynponyms.length >0) {
			    	VerbSynset set = verbHynponyms[0];
					String [] listSimilar = set.getWordForms();
			    	for (String list: listSimilar) {
			    		listSyn=listSyn+ list+",";
			    	}
				}
			    suggestMessage =suggestMessage + verbSynset.getWordForms()[0] +":"+ verbSynset.getDefinition() +"\n Synonym: "+listSyn+ "\n \n";
			    if (i>=3)
			    	break;
			}
		}
		if (suggestMessage.equals("Synonym:")) {
			return null;
		}
		match.setMessages(suggestMessage);
		match.setPosition(position);

		return match;
	}

}
