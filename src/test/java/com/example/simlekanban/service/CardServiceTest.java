package com.example.simlekanban.service;

import com.example.simlekanban.dto.CardRequestDto;
import com.example.simlekanban.entity.Card;
import com.example.simlekanban.entity.CardList;
import com.example.simlekanban.exception.EntityNotFoundException;
import com.example.simlekanban.repository.CardRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceTest {

    private static final Long CARD_1_ID = 1L;
    private static final Long CARD_2_ID = 2L;
    private static final String CARD_1_TITLE = "card1";
    private static final String CARD_2_TITLE = "card2";
    private static final Long CARD_LIST_ID = 1L;
    private static final Long EMPTY_CARD_LIST_ID = 2L;


    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardListService listService;

    @Before
    public void setUp() {
        CardList list = new CardList();
        list.setId(CARD_LIST_ID);

        Card card1 = new Card();
        card1.setId(CARD_1_ID);
        card1.setTitle(CARD_1_TITLE);
        card1.setDescription("description1");
        card1.setPosition(65536F);
        card1.setCardList(list);

        Card card2 = new Card();
        card2.setId(CARD_2_ID);
        card2.setTitle(CARD_2_TITLE);
        card2.setPosition(131072F);
        card2.setCardList(list);

        List<Card> cards = Arrays.asList(card1, card2);

        when(cardRepository.findByCardList_IdOrderByPosition(CARD_LIST_ID))
                .thenReturn(cards);
        when(cardRepository.findByCardList_IdOrderByPosition(EMPTY_CARD_LIST_ID))
                .thenReturn(Collections.emptyList());
        when(cardRepository.findById(card1.getId()))
                .thenReturn(Optional.of(card1));
        when(cardRepository.findById(-99L))
                .thenReturn(Optional.empty());
        when(cardRepository.save(any(Card.class)))
            .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void shouldReturnListOfCardsForProperListId() {
        List<Card> allCards = cardService.getCardsByListId(CARD_LIST_ID);
        Assertions.assertThat(allCards).hasSize(2)
                .extracting(Card::getTitle)
                .contains(CARD_1_TITLE, CARD_2_TITLE);
    }

    @Test
    public void shouldReturnCardByProperId() {
        Card card = cardService.getCardById(CARD_1_ID);
        assertEquals(CARD_1_TITLE, card.getTitle());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionForIncorrectCardId() {
        Long cardId = -99L;
        cardService.getCardById(cardId);
    }

    @Test
    public void shouldSetCardPositionForEmptyList() {
        CardList list = new CardList();
        list.setId(EMPTY_CARD_LIST_ID);

        Card card = new Card();
        Float expectedPosition = 65536F;

        Card createdCard = cardService.createCard(list, card);
        assertEquals(expectedPosition, createdCard.getPosition());
    }

    @Test
    public void shouldSetNewCardPositionForExistList() {
        CardList list = new CardList();
        list.setId(CARD_LIST_ID);

        Card card = new Card();
        Float expectedPosition = 196608F;

        Card createdCard = cardService.createCard(list, card);
        assertEquals(expectedPosition, createdCard.getPosition());
    }

    @Test
    public void shouldSetNewCardDescription() {
        String expectedDescription = "description2";
        CardRequestDto cardDto = new CardRequestDto();
        cardDto.setDescription(expectedDescription);

        Card updatedCard = cardService.updateCard(CARD_1_ID, cardDto);
        assertEquals(expectedDescription, updatedCard.getDescription());
    }

    @Test
    public void shouldSetNewCardList() {
        CardList expectedList = new CardList();
        expectedList.setId(EMPTY_CARD_LIST_ID);

        CardRequestDto cardDto = new CardRequestDto();
        cardDto.setListId(expectedList.getId());

        when(listService.getListById(expectedList.getId())).thenReturn(expectedList);

        Card updatedCard = cardService.updateCard(CARD_1_ID, cardDto);
        assertEquals(expectedList, updatedCard.getCardList());
    }
}