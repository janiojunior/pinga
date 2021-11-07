package br.unitins.topicos12021.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.unitins.topicos12021.dao.MarcaDAO;
import br.unitins.topicos12021.model.Marca;

@FacesConverter(forClass = Marca.class)
public class MarcaConverter implements Converter<Marca>{

	@Override
	public Marca getAsObject(FacesContext context, UIComponent component, String value) {
		MarcaDAO dao = new MarcaDAO();
		return dao.buscarPorId(Integer.parseInt(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Marca marca) {
		return marca.getId().toString();
	}

}
