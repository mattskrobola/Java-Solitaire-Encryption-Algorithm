package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;


public class Solitaire {
	
	
	CardNode deckRear;
	
	
	public void makeDeck() {
		
		int[] cardValues = new int[28];
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	
	void jokerA() {
		
		CardNode ptr = deckRear.next;
		CardNode prev = deckRear;
		
		while(ptr.cardValue != 27){
			ptr = ptr.next;
			prev = prev.next;
		}
		if(ptr.next == deckRear){
			ptr = ptr.next;
			deckRear = prev.next;
			deckRear.next = ptr.next;
			ptr.next = deckRear;
			prev.next = ptr;
		}
		else if(ptr == deckRear){
			deckRear = deckRear.next;
			ptr.next = deckRear.next;
			prev.next = deckRear;
			deckRear.next = ptr;
		}
		else{ 
		prev.next = ptr.next;
		prev = prev.next;
		ptr.next = prev.next;
		prev.next = ptr;
		}
		
	}
	
	
	void jokerB() {
		CardNode ptr = deckRear.next;
		CardNode prev = deckRear;
		while(ptr.cardValue != 28){
			ptr = ptr.next;
			prev = prev.next;
		}
		
		for(int i = 0; i < 2; i ++){
			if(ptr.next == deckRear){
				ptr.next = deckRear.next;
				prev.next = deckRear;
				deckRear.next = ptr;
				deckRear = ptr;
				printList(deckRear);
				prev = prev.next;
			
			}
			else if(ptr == deckRear){
				ptr = ptr.next;
				prev.next = ptr;
				deckRear.next = ptr.next;
				ptr.next = deckRear;
				deckRear = ptr;
				ptr = ptr.next;
				prev = prev.next;
				
			}
			else{
				prev.next = ptr.next;
				prev = prev.next;
				ptr.next = prev.next;
				prev.next = ptr;
			}
		}
		
	  
	}
	
	
	void tripleCut() {
		CardNode ptr = deckRear.next;
		CardNode prev = deckRear;
		if(deckRear.cardValue == 28){
			do{
				deckRear = deckRear.next;
			}while(deckRear.next.cardValue != 27 );
			return;
		}
		 else if(deckRear.cardValue == 27 ){
			do{
				deckRear = deckRear.next;
			}while(deckRear.next.cardValue != 28);
			return;
		}
		 else if(deckRear.next.cardValue == 27){
			 do{
				 deckRear = deckRear.next;
			 }while(deckRear.cardValue != 28);
			 return; 
		 }
		 else if(deckRear.next.cardValue == 28){
			 do{
				 deckRear = deckRear.next;
			 }while(deckRear.cardValue != 27);
			 return; 
		 }
		else{
			boolean AorB = false;
			 for(int i = 0; i < 28;i++){
				 if(ptr.cardValue == 28){
					 AorB = true;
					 continue;
				 }
				 else if(ptr.cardValue == 27){
					 continue;
				 }
				 ptr = ptr.next;
				 prev = prev.next;
			 
			 
			 }
	
			 if(AorB == true){
				 while(ptr.cardValue != 27){
					 ptr = ptr.next;
				 }
		
			 }
			 else{
				 while(ptr.cardValue != 28){
					 ptr = ptr.next;
				 }
				
				 }
		
			 CardNode temp = deckRear.next;
			 deckRear.next = prev.next;
		
			 prev.next = ptr.next;
			
			 ptr.next = temp;
			 deckRear = prev;
			 
			
				 
				 
			 }
		
		  
	}
		
	
		
		
	
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {		
		int count = deckRear.cardValue;
		if(count == 27 || count == 28){
			return;
		}
		CardNode prev = deckRear;
		while(prev.next != deckRear){
			prev = prev.next;
		}
		prev.next = deckRear.next;
		for(int i =0; i < count; i ++){
			prev = prev.next;
		}
		CardNode temp = prev.next;
		prev.next = deckRear;
		deckRear.next = temp;
		
		
		
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
	
		jokerA();
		
		jokerB();
	
		tripleCut();
	
		countCut();
		CardNode ptr = deckRear;
		int num =	ptr.next.cardValue;
		
		if(num == 28){
			num = 27;
		}
		while(num != 0){
			ptr = ptr.next;
			num --;
		}
		if(ptr.next.cardValue == 27 | ptr.next.cardValue == 28){
			return getKey();
		}
		else{
			return ptr.next.cardValue;
		}
		
	
	}
	
	
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	
	public String encrypt(String message) {	
		String updated = "";
		for(int i = 0; i < message.length(); i ++){
			if(Character.isLetter(message.charAt(i))){
				updated += message.charAt(i);
			}
		}
		updated = updated.toUpperCase();
		int cn = 0;
		String fm = "";
		int[] keystream = new int[updated.length()];
		int[] code = new int[updated.length()];
		
		for(int i = 0; i < updated.length();i++){
			keystream[i] = getKey();
		}
		for(int i = 0; i < updated.length();i++){
			System.out.println(keystream[i]);
		}
		for(int i = 0; i < updated.length();i++){
			char a = updated.charAt(i);
			cn  = (a - 'A' + 1) + keystream[i];
			if(cn > 26){
				cn = cn - 26;
			}
			code[i] = cn;
		}
		for(int i = 0; i < updated.length();i++){
			fm+= (char)(code[i]-1+'A');
		} 
		return fm;
	
	    
	}
	
	
	public String decrypt(String message) {	
		String updated = "";
		for(int i = 0; i < message.length(); i ++){
			if(Character.isLetter(message.charAt(i))){
				updated += message.charAt(i);
			}
		}
		updated = updated.toUpperCase();
		int cn = 0;
		String fm = "";
		int[] keystream = new int[updated.length()];
		int[] code = new int[updated.length()];
		
		for(int i = 0; i < updated.length();i++){
			keystream[i] = getKey();
		}
		
		for(int i = 0; i < updated.length();i++){
			char a = updated.charAt(i);
			cn  = (a - 'A' + 1) - keystream[i];
			if(cn <= 0){
				cn = cn + 26;
			}
			code[i] = cn;
		}
		for(int i = 0; i < updated.length();i++){
			fm+= (char)(code[i]-1+'A');
		} 
		return fm;
	} 
		
}


