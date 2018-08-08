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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class CardServiceTest {

    @TestConfiguration
    static class CardServiceTestContextConfiguration {

        @Bean
        public CardService cardService() {
            return new CardService();
        }
    }

    @Autowired
    private CardService cardService;

    @MockBean
    private CardRepository cardRepository;

    @MockBean
    private CardListService listService;

    @Before
    public void setUp() {
        CardList list = new CardList();
        list.setId(1L);

        Card card1 = new Card();
        card1.setId(1L);
        card1.setTitle("card1");
        card1.setDescription("description1");
        card1.setPosition(65536F);
        card1.setCardList(list);

        Card card2 = new Card();
        card2.setId(2L);
        card2.setTitle("card2");
        card2.setPosition(131072F);

        List<Card> cards = Arrays.asList(card1, card2);

        Mockito.when(cardRepository.findByCardList_IdOrderByPosition(1L))
                .thenReturn(cards);
        Mockito.when(cardRepository.findById(card1.getId()))
                .thenReturn(Optional.of(card1));
        Mockito.when(cardRepository.findById(-99L))
                .thenReturn(Optional.empty());
        Mockito.when(cardRepository.save(any(Card.class)))
            .thenAnswer(i -> i.getArguments()[0]);

        Mockito.when(listService.getListById(1L)).thenReturn(list);
    }

    @Test
    public void shouldReturnListOfCardsForProperListId() {
        String title1 = "card1";
        String title2 = "card2";
        Long listId = 1L;

        List<Card> allCards = cardService.getCardsByListId(listId);
        Assertions.assertThat(allCards).hasSize(2)
                .extracting(Card::getTitle)
                .contains(title1, title2);
    }

    @Test
    public void shouldReturnCardByProperId() {
        Long cardId = 1L;
        String expectedTitle = "card1";

        Card card = cardService.getCardById(cardId);
        assertEquals(card.getTitle(), expectedTitle);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionForIncorrectCardId() {
        Long cardId = -99L;
        cardService.getCardById(cardId);
    }

    @Test
    public void shouldSetCardPositionForEmptyList() {
        CardList list = new CardList();
        list.setId(2L);

        Card card = new Card();
        Float expectedPosition = 65536F;

        Card createdCard = cardService.createCard(list, card);
        assertEquals(expectedPosition, createdCard.getPosition());
    }

    @Test
    public void shouldSetNewCardPositionForExistList() {
        CardList list = new CardList();
        list.setId(1L);

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
        Long cardId = 1L;

        Card updatedCard = cardService.updateCard(cardId, cardDto);
        assertEquals(expectedDescription, updatedCard.getDescription());
    }

    @Test
    public void shouldSetNewCardList() {
        CardList expectedList = new CardList();
        expectedList.setId(2L);

        CardRequestDto cardDto = new CardRequestDto();
        cardDto.setListId(expectedList.getId());
        Long cardId = 1L;

        Mockito.when(listService.getListById(expectedList.getId())).thenReturn(expectedList);

        Card updatedCard = cardService.updateCard(cardId, cardDto);
        assertEquals(expectedList, updatedCard.getCardList());
    }
}