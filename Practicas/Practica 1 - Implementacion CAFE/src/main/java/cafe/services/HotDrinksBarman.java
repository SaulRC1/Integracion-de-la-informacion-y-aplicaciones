package cafe.services;

import cafe.models.message.MessageBuilder;
import cafe.services.barman.drinks.Drink;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class HotDrinksBarman
{
    private final List<Drink> hotDrinksStorage = new ArrayList<>();
    
    public HotDrinksBarman()
    {
        Drink cafe = new Drink("cafe", "hot", "Nescafé", "150ml",
                "cup of coffee");
        hotDrinksStorage.add(cafe);
        
        Drink chocolate = new Drink("chocolate", "hot", "Valor", "250ml", 
                "glass");
        hotDrinksStorage.add(chocolate);
        
        Drink te = new Drink("te", "hot", "Hornimans", "250ml", 
                "glass");
        hotDrinksStorage.add(te);
        
        Drink tila = new Drink("tila", "hot", "La Barraca", "250ml",
                "glass");
        hotDrinksStorage.add(tila);
    }
    
    /**
     * Returns the drink that has the name passed by parameter.
     *
     * @param drinkName The drink's name.
     * @return The drink that has that name.
     */
    public Drink getDrink(String drinkName)
    {
        for (Drink drink : hotDrinksStorage)
        {
            if (drink.getName().equals(drinkName))
            {
                return drink;
            }
        }

        return null;
    }
    
    /**
     * Formats the {@link Drink} information to the expected xml format by the CAFE 
     * application.
     * 
     * @param drinkName The {@link Drink}'s name.
     * @return An xml document with the drink information.
     */
    public Document formatDrinkInformation(String drinkName)
    {
        MessageBuilder messageBuilder = new MessageBuilder();
        
        Document drinkInformation = messageBuilder.buildEmptyMessage().getDocument();
        
        Drink drink = getDrink(drinkName);
        
        if(drink != null)
        {
            Node rootElement = drinkInformation.createElement("drink_information");
            drinkInformation.appendChild(rootElement);
            
            Node brandNode = drinkInformation.createElement("brand");
            brandNode.setTextContent(drink.getBrand());
            rootElement.appendChild(brandNode);
            
            Node quantityServedNode = drinkInformation.createElement("quantity_served");
            quantityServedNode.setTextContent(drink.getQuantityServed());
            rootElement.appendChild(quantityServedNode);
            
            Node servingStyleNode = drinkInformation.createElement("serving_style");
            servingStyleNode.setTextContent(drink.getServingStyle());
            rootElement.appendChild(servingStyleNode);
        }
        
        return drinkInformation;
    }
}
