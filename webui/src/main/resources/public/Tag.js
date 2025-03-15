export const Tag = Backbone.Model.extend(
{
	name: function()
	{
		return this.get('name');
	}
	,
	count: function()
	{
		return this.get('count');
	}
	,
	readonly: function()
	{
		return this.get('readonly') === true;
	}
	,
	readonly_set: function(v) {
		return this.set('readonly', v);
	}
	,
	name_set: function( v )
	{
		return this.set('name', v);
	}
});

export const TagsCollection = Backbone.Collection.extend(
{
	model: Tag,
	url: 'api/collection/collections.json'
});

export const TagSuggestionsCollection = Backbone.Collection.extend(
{
	model: Tag,
	url: 'api/collection/tag-suggestions.json'
});


