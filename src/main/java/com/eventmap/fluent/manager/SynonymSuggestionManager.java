package com.eventmap.fluent.manager;



import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import com.eventmap.fluent.Main;

import edu.smu.tspell.wordnet.AdjectiveSynset;
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.VerbSynset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.util.CoreMap;

@Component
@EnableAutoConfiguration
public class SynonymSuggestionManager {
	List<String> verbNodeNames = Arrays.asList(new String[]{"VB","VBD","VBG","VBN","VBP","VBZ"});
	List<String> adjNodenames = Arrays.asList(new String[]{"JJ","JJR ","JJS "});
	List<String> verbList;
	List<String> adjList;
	
	public void suggestWordProcess(String text) {
	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    LexicalizedParser lp = LexicalizedParser.loadModel(            "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz" );
	    lp.setOptionFlags(new String[]{"-maxLength", "500", "-retainTmpSubcategories"});
	    TokenizerFactory tokenizerFactory = PTBTokenizer.factory( new CoreLabelTokenFactory(), "");
	    List wordList = tokenizerFactory.getTokenizer(new StringReader(text)).tokenize();
	    Tree tree = lp.apply(wordList);
	    System.out.println(tree);
		verbList = new ArrayList<String>();
		adjList = new ArrayList<String>();
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
		
		for (String verb: verbList) {
			synonymSuggestion(verb, "VP");
		}
		for (String adj: adjList) {
			synonymSuggestion(adj,"ADJ");
		}
		System.out.println("Finish");

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
                    System.out.print(String.format("(%s - "+category+"),",word.word()));
                    verbList.add(word.word());
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
                    System.out.print(String.format("(%s - "+category+"),",word.word()));
                    adjList.add(word.word());
	        	}
	        }
	    }


	    List<Tree> kids = tree.getChildrenAsList();
	    for (Tree kid : kids) {
	        collect(kid, foundPhraseNodes);
	    }
	}
	
	private void getAllSynonym() {
		for (String verb: verbList) {
			synonymSuggestion(verb, "VP");
		}
		for (String adj: adjList) {
			synonymSuggestion(adj,"ADJ");
		}
	}
	public void synonymSuggestion(String word, String type) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		System.setProperty("wordnet.database.dir", classloader.getResource("dict").getPath());
		NounSynset nounSynset; 
		NounSynset[] hyponyms; 
		AdjectiveSynset adjSyn;
		AdjectiveSynset[] adjHynponyms;
		VerbSynset verbSynset;
		VerbSynset[] verbHynponyms;

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
			    for (AdjectiveSynset set: adjHynponyms) {
			    	System.out.println(set.toString());
			    }
			}
		} else if (type.equals("VP")) {
			wordType = SynsetType.VERB;
			Synset[] synsets = database.getSynsets(word, wordType); 
			for (int i = 0; i < synsets.length; i++) { 
				verbSynset = (VerbSynset)(synsets[i]); 
				verbHynponyms = verbSynset.getVerbGroup(); 
			    System.out.println(verbSynset.toString() + 
			            ": " + verbSynset.getDefinition() + ") has " + verbHynponyms.length + " hyponyms"); 
			    for (VerbSynset set: verbHynponyms) {
			    	System.out.println(set.toString());
			    }
			}
		}
	}

}
