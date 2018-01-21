<?xml version="1.0" encoding="UTF-8"?>
<html xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xsl:version="1.0">
	<head>
		<title>Googlescholar</title>
	</head>
	<body>
		<h1>Nuno Laranjeiro</h1>
		<table border="1">
			<tr>
				<td>Title</td>
				<td>Authors</td>
				<td>Pub_date</td>
				<td>Description</td>
				<td>Citations number</td>
				<td>List of citations</td>
			</tr>
			<xsl:for-each select="//Publication">
				<tr>
					<td>
						<xsl:value-of select="Title" />
					</td>
					<td>
						<xsl:value-of select="Authors" />
					</td>
					<td>
						<xsl:value-of select="Publication_date" />
					</td>
					<td>
						<xsl:value-of select="Description" />
					</td>
					<td>
						<xsl:value-of select="Citations/Quantity" />
					</td>
					<xsl:for-each select="Citations/Citation">
						<tr>
							<td>
								<xsl:value-of select="Title" />
							</td>
						</tr>
					</xsl:for-each>
				</tr>
			</xsl:for-each>
		</table>
		<table border="2">
			<tr>
				<td>Total number of citations</td>
			</tr>
			<tr>
				<td>
					<xsl:value-of select="//Total_num_cit_autor" />
				</td>
			</tr>
		</table>
		
		<table border="2">
			<tr>
				<th>Number of citations</th>
				<th>Year</th>
			</tr>
			<tr>
				<xsl:for-each select="//Ano">
					<tr>
						<td>
							<xsl:value-of select="Num" />
						</td>
						<td>
							<xsl:value-of select="Id" />
						</td>
					</tr>
				</xsl:for-each>
			</tr>
		</table>
	</body>
</html>