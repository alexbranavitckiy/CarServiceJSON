package com.netcracker.file.services.impl.outfit;

import static org.mockito.Mockito.verify;

import com.netcracker.errors.EmptySearchException;
import com.netcracker.outfit.Outfit;
import com.netcracker.file.services.impl.CRUDServicesImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.when;

public class OutfitsServicesImplTest {

  @Mock
  private CRUDServicesImpl<Outfit,UUID> crudServices ;

  private final Outfit outfit = Outfit.builder().id(UUID.randomUUID()).build();

  @Spy
  private List<Outfit> outfitList = new ArrayList<>(List.of(outfit, outfit));

  @InjectMocks
  private OutfitsServicesImpl outfitsServices = new OutfitsServicesImpl();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAllOutfitsShouldReturnTrue() throws EmptySearchException {
    when(crudServices.getAll(Mockito.anyObject(), Mockito.any())).thenReturn(outfitList);
    Assert.assertEquals(outfitsServices.getAllOutfits(), outfitList);
    verify(outfitsServices.getAllOutfits());
    Assert.assertEquals(outfitsServices.getAllOutfits(), outfitList);
  }

  @Test
  public void testAddObjectInOutfitsShouldReturnTrue() {
    when(crudServices.addObject(Mockito.anyObject(), Mockito.any(), Mockito.any())).thenReturn(
      true);
    Assert.assertTrue(outfitsServices.addObjectInOutfits(outfit));
  }

  @Test
  public void testUpdateOutfitShouldReturnTrue() {
    when(crudServices.updateObject(Mockito.anyObject(), Mockito.any(), Mockito.any())).thenReturn(
      true);
    Assert.assertTrue(outfitsServices.updateOutfit(outfit));
  }

  @Test
  public void testAddObjectInOutfitsShouldReturnFalse() {
    when(crudServices.addObject(null, new File(""), Outfit[].class)).thenReturn(false);
    Assert.assertFalse(outfitsServices.addObjectInOutfits(outfit));
  }

  @Test
  public void testUpdateOutfitShouldReturnFalse() {
    when(crudServices.updateObject(null, new File(""), Outfit[].class)).thenReturn(false);
    Assert.assertFalse(outfitsServices.updateOutfit(null));
  }
}